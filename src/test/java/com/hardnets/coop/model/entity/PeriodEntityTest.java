package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

class PeriodEntityTest {

    @Test
    @DisplayName("Prueba constructor de Period Entity")
    void create_success() {
        PeriodEntity periodEntity = new PeriodEntity();
        assertEquals(null, periodEntity.getStartDate());
        assertEquals(null, periodEntity.getEndDate());
        assertEquals(PeriodStatusEnum.ACTIVE, periodEntity.getStatus());
        assertEquals(0, periodEntity.getConsumptions().size());
    }

}
