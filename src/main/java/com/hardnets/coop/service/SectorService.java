package com.hardnets.coop.service;

import com.hardnets.coop.model.entity.SectorEntity;

import java.util.List;

public interface SectorService {

    List<SectorEntity> findAll();

    SectorEntity save(SectorEntity sector);
}
