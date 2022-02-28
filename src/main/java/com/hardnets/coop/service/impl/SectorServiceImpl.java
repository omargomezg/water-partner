package com.hardnets.coop.service.impl;

import com.hardnets.coop.model.entity.SectorEntity;
import com.hardnets.coop.repository.SectorRepository;
import com.hardnets.coop.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    @Override
    public List<SectorEntity> findAll() {
        return sectorRepository.findAll();
    }

    @Override
    public SectorEntity save(SectorEntity sector) {
        var sectorDB = sectorRepository.findAllByNameEquals(sector.getName());
        if (sectorDB.isPresent()) {
            sectorDB.get().setName(sector.getName());
            return sectorRepository.save(sectorDB.get());
        }
        return sectorRepository.save(sector);
    }
}
