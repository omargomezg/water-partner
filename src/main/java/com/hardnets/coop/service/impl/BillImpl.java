package com.hardnets.coop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.libreDte.boletaTerceros.BoletaTercerosRequest;
import com.hardnets.coop.model.libreDte.boletaTerceros.DetalleBoletaTerceros;
import com.hardnets.coop.model.libreDte.boletaTerceros.Response;
import com.hardnets.coop.model.libreDte.constant.DocumentTypeEnum;
import com.hardnets.coop.repository.BillDetailRepository;
import com.hardnets.coop.repository.BillRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.SaleDocumentService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * Tareas relacionadas a la gestión de boletas de servicios
 */
@AllArgsConstructor
@Log4j2
@Service
public class BillImpl implements SaleDocumentService<BillEntity> {

    private final PeriodRepository periodRepository;
    private final ConsumptionRepository consumptionRepository;
    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final BillDetailService billDetailService;

    @Override
    public BillEntity getById(Long id) {
        return null;
    }

    @Override
    public void createByClient(String rut) {

    }

    @Override
    public void emitDocumentTaxElectronic() {
        ObjectMapper objectMapper = new ObjectMapper();
        BoletaTercerosRequest boleta = new BoletaTercerosRequest();
        DetalleBoletaTerceros detalle = new DetalleBoletaTerceros();
        detalle.setDescription("Consumo de agua potable");
        detalle.setQuantity(1L);
        detalle.setUnitAmount(2300L);
        boleta.getDetalle().add(detalle);
        boleta.getEncabezado().getDocumento().setTipoDTE(DocumentTypeEnum.DTE.getValue());
        boleta.getEncabezado().getDocumento().setFolio(1);
        boleta.getEncabezado().getCompany().setRut("73741900-2");
        boleta.getEncabezado().getClient().setRut("14081226-9");
        String result = callGet(boleta);
        try {
            Response response = objectMapper.readValue(result, Response.class);
            Optional<BillEntity> bill = billRepository.findById(101L);
            if (bill.isPresent()) {
                bill.get().getIntegration().setHash(response.getHash());
                billRepository.save(bill.get());
            }
            System.out.printf(response.toString());
        } catch (JsonProcessingException jsonMappingException) {
            log.error(jsonMappingException);
        }
    }

    @Override
    public void generateDte() {

    }

    @Override
    public void updateStatusDte(String trackId) {

    }

    @Override
    public void generatePdf() {

    }

    @Async
    @Override
    public void createAllInPeriod(long periodId) {
        PeriodEntity period = periodRepository.getById(periodId);
        List<ConsumptionEntity> consumptions = consumptionRepository.findAllByPeriod(period);
        for (ConsumptionEntity consumption : consumptions) {
            if (consumption.getWaterMeter().getClient() != null) {
                BillEntity bill = new BillEntity();
                bill.setClient(consumption.getWaterMeter().getClient());
                bill.setStatus(SalesDocumentStatusEnum.OUTSTANDING);
                bill.setWaterMeter(consumption.getWaterMeter());
                BillEntity dbBill = billRepository.save(bill);
                List<BillDetailEntity> detail = billDetailService.getDetail(consumption, dbBill.getId());
                detail.forEach(billDetailRepository::save);
                billRepository.save(bill);
            }
        }
    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void sendToClient(Long id) {

    }

    private String callGet(Object object) {
        String result = "";
        List<MediaType> acceptableMediaTypes = new ArrayList<>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptableMediaTypes);
        var restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(
                new BasicAuthenticationInterceptor(
                        "4ow4Ql9BE8tO5LReQIzC2lwx7PyfpiHF",
                        Base64.getEncoder().encodeToString("X".getBytes())
                )
        );
        ObjectMapper obj = new ObjectMapper();
        try {
            HttpEntity<String> entity = new HttpEntity<>(obj.writeValueAsString(object), headers);
            result = restTemplate.postForObject("https://libredte.cl/api/dte/documentos/emitir?normalizar=1&formato=json&links=0&email=0", entity, String.class);
        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException);
        }
        return result;
    }
}
