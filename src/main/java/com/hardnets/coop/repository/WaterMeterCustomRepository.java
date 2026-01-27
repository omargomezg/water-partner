package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.WaterMeterFilterRequest;
import com.hardnets.coop.model.entity.WaterMeterEntity;

import java.util.List;

public interface WaterMeterCustomRepository {
    
    List<WaterMeterEntity> getAllByPage(WaterMeterFilterRequest filter);
    
    Long getTotalOfElements(WaterMeterFilterRequest filter);
}
