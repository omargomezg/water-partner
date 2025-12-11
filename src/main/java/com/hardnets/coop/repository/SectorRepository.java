package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.SectorEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<SectorEntity, Long> {

    Optional<SectorEntity> findAllByNameEquals(String name);
    
    Optional<SectorEntity> findByName(String name);
}
