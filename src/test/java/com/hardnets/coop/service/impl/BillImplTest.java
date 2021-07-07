package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.entity.BillDetailEntity;
import com.hardnets.coop.model.entity.BillEntity;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.CompanyEntity;
import com.hardnets.coop.repository.BillDetailRepository;
import com.hardnets.coop.repository.BillRepository;
import com.hardnets.coop.repository.ConsumptionRepository;
import com.hardnets.coop.repository.LibreDteRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.service.SiiService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BillImplTest {

    private BillImpl bill;

    @Mock
    private PeriodRepository periodRepository;
    @Mock
    private ConsumptionRepository consumptionRepository;
    @Mock
    private BillRepository billRepository;
    @Mock
    private BillDetailRepository billDetailRepository;
    @Mock
    private BillDetailService billDetailService;
    @Mock
    private LibreDteRepository libreDteRepository;
    @Mock
    private SiiService siiService;


    @Test
    public void createAllInPeriod() {
        bill.emitDocumentTaxElectronic(102L);
    }

    @Before
    public void setup() {
        bill = new BillImpl(periodRepository, consumptionRepository, billRepository, billDetailRepository, billDetailService, libreDteRepository, siiService);
        var bill = getBillEntity();
        when(billRepository.findById(102L)).thenReturn(Optional.of(bill));
    }

    private BillEntity getBillEntity() {
        BillEntity billEntity = new BillEntity();
        billEntity.getDetail().add(mock(BillDetailEntity.class));
        billEntity.getDetail().add(mock(BillDetailEntity.class));
        billEntity.setCompany(mock(CompanyEntity.class));
        billEntity.setClient(mock(ClientEntity.class));
        return billEntity;
    }
}
