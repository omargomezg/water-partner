package com.hardnets.coop.service;

import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class WaterMeterServiceTest {

    @Mock
    WaterMeterRepository waterMeterRepository;

    @Mock
    ClientRepository clientRepository;

    @Mock
    SubsidyRepository subsidyRepository;

    @Mock
    TariffRepository tariffRepository;

    @Mock
    PeriodRepository periodRepository;


    @Test
    void update() {

    }

    @Test
    void testUpdate() {
    }

    @Test
    void create() {
    }

    @Test
    void testCreate() {
    }

    @Test
    void existSerial() {
    }

    @Test
    void getBySerial() {
    }

    @Test
    void getById() {
    }

    @Test
    void findAllWhereNotRelated() {
    }

    @Test
    void getAll() {
    }

    @Test
    void getByUser() {
    }

    @Test
    void relateToClient() {
    }

    @Test
    void findAllForSetConsumption() {
    }
}
