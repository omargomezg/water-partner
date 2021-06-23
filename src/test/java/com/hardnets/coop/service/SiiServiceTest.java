package com.hardnets.coop.service;

import com.hardnets.coop.model.constant.AttributeKeyEnum;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.CompanyEntity;
import com.hardnets.coop.model.entity.GenericAttributeEntity;
import com.hardnets.coop.model.libreDte.boletaTerceros.BoletaTercerosRequest;
import com.hardnets.coop.model.libreDte.boletaTerceros.DetalleBoletaTerceros;
import com.hardnets.coop.model.libreDte.boletaTerceros.Response;
import com.hardnets.coop.model.libreDte.constant.DocumentTypeEnum;
import com.hardnets.coop.repository.GenericAttributeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SiiServiceTest {

    BillEntity bill = new BillEntity();
    CompanyEntity company = new CompanyEntity();
    private SiiService siiService;

    @Mock
    private GenericAttributeRepository genericAttributeRepository;
    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        siiService = new SiiService(genericAttributeRepository, restTemplate);
        company.setId(1L);
        company.setIdentifier("123456789");
        bill.setCompany(company);
        bill.getIntegration().setFolio(12L);
        GenericAttributeEntity hashAttribute = new GenericAttributeEntity();
        hashAttribute.setValue("theHash");
        GenericAttributeEntity urlAttribute = new GenericAttributeEntity();
        urlAttribute.setValue("https://libredte.cl");
        when(genericAttributeRepository.findByCompanyIdAndAttributeKey(bill.getCompany().getId(), AttributeKeyEnum.LIBRE_DTE_URL)).thenReturn(Optional.of(urlAttribute));
        when(genericAttributeRepository.findByCompanyIdAndAttributeKey(bill.getCompany().getId(), AttributeKeyEnum.LIBRE_DTE_HASH)).thenReturn(Optional.of(hashAttribute));
    }

    @Test
    public void emitTemporalDte_success() {
        String postUrl = "https://libredte.cl/api/dte/documentos/emitir?normalizar=1&formato=json&links=0&email=0";
        BoletaTercerosRequest boleta = getBoletaTerceros();
        boleta.getDetalle().add(getDetalleBoletaTerceros(2300L, "Concepto 1"));
        boleta.getDetalle().add(getDetalleBoletaTerceros(1200L, "Concepto 2"));
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        var entity = getHttpEntity("{\"Encabezado\":{\"IdDoc\":{\"TipoDTE\":38,\"Folio\":1},\"Emisor\":{\"RUTEmisor\":\"123456789\"},\"Receptor\":{\"RUTRecep\":\"675435434\",\"RznSocRecep\":\"\",\"GiroRecep\":\"\",\"DirRecep\":\"\",\"CmnaRecep\":\"\"}},\"Detalle\":[{\"NmbItem\":\"Concepto 1\",\"QtyItem\":1,\"PrcItem\":2300},{\"NmbItem\":\"Concepto 2\",\"QtyItem\":1,\"PrcItem\":1200}]}");
        when(restTemplate.postForObject(postUrl, entity, String.class)).thenReturn("{\"emisor\":73741900,\"receptor\":14081226,\"dte\":33,\"codigo\":\"628dd16732ffe3f303cb23be78084465\"}");

        String result = siiService.emitTemporalDte(boleta, company);
        assertNotNull(result);
    }

    @Test
    public void generate_success() {
        Response body = new Response();
        body.setClientRut("140816629");
        body.setCompanyRut("73741900");
        body.setDocumentType(DocumentTypeEnum.DTE.getValue());
        body.setHash("587ccc1706a77a21833d0f1734fd0acc");
        var entity = getHttpEntity("{\"emisor\":\"73741900\",\"receptor\":\"140816629\",\"dte\":38,\"codigo\":\"587ccc1706a77a21833d0f1734fd0acc\"}");
        when(restTemplate.postForObject("https://libredte.cl/api/dte/documentos/generar?getXML=0&links=1&email=0&retry=10&gzip=0",
                entity, String.class)).thenReturn("{\"emisor\":73741900,\"receptor\":140816629,\"dte\":33,\"codigo\":\"628dd16732ffe3f303cb23be78084465\"}");
        String response = siiService.generate(body, company);
        assertNotNull(response);
        assertEquals("{\"emisor\":73741900,\"receptor\":140816629,\"dte\":33,\"codigo\":\"628dd16732ffe3f303cb23be78084465\"}", response);
    }

    @Test
    public void generate_fail() {

    }

    @Test
    public void pdfDeUnDteEmitido_success() {
        String response = siiService.pdfDeUnDteEmitido(bill);
        assertNotEquals("", response);
        assertNotNull(response);
    }

    private BoletaTercerosRequest getBoletaTerceros() {
        BoletaTercerosRequest boleta = new BoletaTercerosRequest();
        boleta.getEncabezado().getDocumento().setTipoDTE(DocumentTypeEnum.DTE.getValue());
        boleta.getEncabezado().getDocumento().setFolio(1);
        boleta.getEncabezado().getCompany().setRut("123456789");
        boleta.getEncabezado().getClient().setRut("675435434");
        return boleta;
    }

    private HttpEntity<String> getHttpEntity(String body) {
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        return new HttpEntity<>(body, headers);
    }

    private DetalleBoletaTerceros getDetalleBoletaTerceros(Long amount, String concept) {
        return new DetalleBoletaTerceros(concept, 1L, amount);
    }
}
