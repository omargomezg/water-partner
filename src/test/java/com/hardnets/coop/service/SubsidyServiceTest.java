package com.hardnets.coop.service;

import com.hardnets.coop.model.dto.request.UserSubsidyRequest;
import com.hardnets.coop.model.dto.request.userSubsidyRequest.Decree;
import com.hardnets.coop.model.dto.request.userSubsidyRequest.Subsidy;
import com.hardnets.coop.model.dto.request.userSubsidyRequest.WaterMeter;
import com.hardnets.coop.model.entity.DecreeEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.repository.DecreeRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubsidyServiceTest {

    private SubsidyService subsidyService;

    @Mock
    private SubsidyRepository subsidyRepository;
    @Mock
    private WaterMeterRepository waterMeterRepository;
    @Mock
    private DecreeRepository decreeRepository;

    private SubsidyEntity dbSubsidy;
    private DecreeEntity dbDecree;
    private UserSubsidyRequest request;


    @Before
    public void setup() {
        this.subsidyService = new SubsidyService(subsidyRepository, waterMeterRepository, decreeRepository);
        dbSubsidy = new SubsidyEntity();
        dbSubsidy.setId(12L);
        dbDecree = new DecreeEntity();
        request = new UserSubsidyRequest();
    }

    @Test
    public void update_success() {
        request.setWaterMeter(new WaterMeter());
        request.setSubsidy(new Subsidy());
        request.setDecree(new Decree());
        when(subsidyRepository.findById(request.getSubsidy().getId())).thenReturn(Optional.of(dbSubsidy));
        when(decreeRepository.findFirstByNumberEquals(request.getDecree().getNumber())).thenReturn(Optional.of(dbDecree));
        when(subsidyRepository.save(dbSubsidy)).thenReturn(dbSubsidy);
        SubsidyEntity response = subsidyService.update(request);
        assertEquals(12L, response.getId());
    }
}
