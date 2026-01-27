package com.hardnets.coop.repository;

import java.util.Optional;

import com.hardnets.coop.model.dto.SectorFilterRequest;
import com.hardnets.coop.model.entity.SectorEntity;

public interface SectorCustomRepository {

    Optional<SectorEntity> findByFilter(SectorFilterRequest filter);

    Optional<SectorEntity> findById(Long id);
    
}
