package com.hardnets.coop.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hardnets.coop.exception.BillNotFoundException;
import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.ConsumptionEntity;
import com.hardnets.coop.model.entity.LibreDteEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.libreDte.boletaTerceros.BoletaTercerosRequest;
import com.hardnets.coop.model.libreDte.boletaTerceros.DetalleBoletaTerceros;
import com.hardnets.coop.model.libreDte.boletaTerceros.Response;
import com.hardnets.coop.model.libreDte.constant.DocumentTypeEnum;
import com.hardnets.coop.repository.BillDetailRepository;
import com.hardnets.coop.repository.BillRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.LibreDteRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.SaleDocumentService;
import com.hardnets.coop.service.SiiService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private final LibreDteRepository libreDteRepository;
    private final SiiService siiService;

    @Override
    public BillEntity getById(Long id) {
        return null;
    }

    @Override
    public void createByClient(String rut) {

    }

    @Async
    @Override
    public void emitDocumentTaxElectronic(Long billId) {
        BillEntity bill = billRepository.findById(billId).orElseThrow(() -> new BillNotFoundException(billId.toString()));
        ObjectMapper objectMapper = new ObjectMapper();
        BoletaTercerosRequest boleta = new BoletaTercerosRequest();
        bill.getDetail().forEach(detail -> boleta.getDetalle().add(setDetail(detail)));
        boleta.getEncabezado().getDocumento().setTipoDTE(DocumentTypeEnum.DTE.getValue());
        boleta.getEncabezado().getDocumento().setFolio(1);
        boleta.getEncabezado().getCompany().setRut(
                bill.getCompany().getIdentifier()
        );
        boleta.getEncabezado().getClient().setRut(
                bill.getClient().getRut()
        );
        String result = siiService.emitTemporalDte(boleta, bill.getCompany());
        try {
            Response response = objectMapper.readValue(result, Response.class);
            createIntegrationData(bill, response);
            bill.getIntegration().setHash(response.getHash());
            billRepository.save(bill);
        } catch (JsonProcessingException jsonMappingException) {
            log.error(jsonMappingException);
        }
    }

    private DetalleBoletaTerceros setDetail(BillDetailEntity detail) {
        DetalleBoletaTerceros detalle = new DetalleBoletaTerceros();
        detalle.setDescription(detail.getConcept());
        detalle.setQuantity(1L);
        detalle.setUnitAmount(detail.getTotalAmount());
        return detalle;
    }

    private void createIntegrationData(BillEntity bill, Response response) {
        LibreDteEntity libreDte = new LibreDteEntity();
        libreDte.setHash(response.getHash());
        libreDte = libreDteRepository.save(libreDte);
        bill.setIntegration(libreDte);
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
                emitDocumentTaxElectronic(dbBill.getId());
            }
        }
    }

    @Override
    public void get(Long id) {

    }

    @Override
    public void sendToClient(Long id) {

    }

}
