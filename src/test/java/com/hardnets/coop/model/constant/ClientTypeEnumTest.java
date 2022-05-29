package com.hardnets.coop.model.constant;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTypeEnumTest {

    @Test
    void checkIsPartner() {
      assertFalse(ClientTypeEnum.isPartner(ClientTypeEnum.PRIVATE));
        assertTrue(ClientTypeEnum.isPartner(ClientTypeEnum.RESIDENT_PARTNER));
        assertTrue(ClientTypeEnum.isPartner(ClientTypeEnum.NO_RESIDENT_PARTNER));
        assertFalse(ClientTypeEnum.isPartner(ClientTypeEnum.PUBLIC));
    }

}
