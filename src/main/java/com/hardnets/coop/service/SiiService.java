package com.hardnets.coop.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardnets.coop.exception.GenericAttributeException;
import com.hardnets.coop.model.constant.AttributeKeyEnum;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.CompanyEntity;
import com.hardnets.coop.model.entity.GenericAttributeEntity;
import com.hardnets.coop.model.libreDte.boletaTerceros.Response;
import com.hardnets.coop.model.libreDte.constant.DocumentTypeEnum;
import com.hardnets.coop.repository.GenericAttributeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Log4j2
@AllArgsConstructor
@Service
public class SiiService {

    private final GenericAttributeRepository genericAttributeRepository;
    private final RestTemplate restTemplate;

    /**
     * Emite un documento temporal
     */
    public String emitTemporalDte(Object object, CompanyEntity company) {
        // return "{\"emisor\":73741900,\"receptor\":14081226,\"dte\":33,\"codigo\":\"628dd16732ffe3f303cb23be78084465\"}";
        GenericAttributeEntity url = genericAttributeRepository.findByCompanyIdAndAttributeKey(company.getId(), AttributeKeyEnum.LIBRE_DTE_URL).orElseThrow(
                GenericAttributeException::new
        );
        GenericAttributeEntity hash = genericAttributeRepository.findByCompanyIdAndAttributeKey(company.getId(), AttributeKeyEnum.LIBRE_DTE_HASH).orElseThrow(
                GenericAttributeException::new
        );
        StringBuilder fullUrl = new StringBuilder()
                .append(url.getValue())
                .append("/api/dte/documentos/emitir?normalizar=1&formato=json&links=0&email=0");
        return callPost(object, hash.getValue(), fullUrl);
    }

    /**
     * Genera el documento a a partir de un temporal
     *
     * @return Un Json {
     * "emisor": 76192083,
     * "receptor": 66666666,
     * "dte": 39,
     * "codigo": "587ccc1706a77a21833d0f1734fd0acc"
     * }
     */
    public String generate(Response body, CompanyEntity company) {
        GenericAttributeEntity url = genericAttributeRepository.findByCompanyIdAndAttributeKey(company.getId(), AttributeKeyEnum.LIBRE_DTE_URL).orElseThrow(
                GenericAttributeException::new
        );
        GenericAttributeEntity hash = genericAttributeRepository.findByCompanyIdAndAttributeKey(company.getId(), AttributeKeyEnum.LIBRE_DTE_HASH).orElseThrow(
                GenericAttributeException::new
        );
        StringBuilder fullUrl = new StringBuilder()
                .append(url.getValue())
                .append("/api/dte/documentos/generar?getXML=0&links=1&email=0&retry=10&gzip=0");
        return callPost(body, hash.getValue(), fullUrl);
    }

    @Nullable
    private String callPost(Object body, String hash, StringBuilder fullUrl) {
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(
                        hash,
                        Base64.getEncoder().encodeToString("X".getBytes())
                )
        );
        ObjectMapper obj = new ObjectMapper();
        try {
            HttpEntity<String> entity = new HttpEntity<>(obj.writeValueAsString(body), headers);
            return restTemplate.postForObject(fullUrl.toString(), entity, String.class);
        } catch (Exception jsonProcessingException) {
            log.error(jsonProcessingException);
        }
        return null;
    }

    public void status() {
    }

    /**
     * Recurso que permite generar el PDF de un documento tributario electrónico emitido en el sistema.
     * url: https://documenter.getpostman.com/view/5911929/SzKQxKjW#3e8ec79e-caae-4c13-a725-0cbe1b870489
     *
     * @param bill
     */
    public String pdfDeUnDteEmitido(BillEntity bill) {
        // https://libredte.cl/api/dte/dte_emitidos/pdf/:dte/:folio/:emisor?formato=general&papelContinuo=0&copias_tributarias=1&copias_cedibles=1&cedible=0&compress=0&base64=0
        String result = "";
        GenericAttributeEntity url = genericAttributeRepository.findByCompanyIdAndAttributeKey(bill.getCompany().getId(), AttributeKeyEnum.LIBRE_DTE_URL).orElseThrow(
                GenericAttributeException::new
        );
        GenericAttributeEntity hash = genericAttributeRepository.findByCompanyIdAndAttributeKey(bill.getCompany().getId(), AttributeKeyEnum.LIBRE_DTE_HASH).orElseThrow(
                GenericAttributeException::new
        );
        StringBuilder fullUrl = new StringBuilder()
                .append(url.getValue())
                .append("/api/dte/dte_emitidos/pdf/")
                .append(DocumentTypeEnum.DTE.getValue())
                .append("/")
                .append(bill.getIntegration().getFolio())
                .append("/")
                .append(bill.getCompany().getIdentifier())
                .append("?formato=general&papelContinuo=0&copias_tributarias=1&copias_cedibles=1&cedible=0&compress=0&base64=0");
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(
                        hash.getValue(),
                        Base64.getEncoder().encodeToString("X".getBytes())
                )
        );
        try {
            log.info(fullUrl.toString());
            result = restTemplate.getForObject(fullUrl.toString(), String.class);
        } catch (Exception jsonProcessingException) {
            log.error(jsonProcessingException);
        }
        return result;
    }
}
