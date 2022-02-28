package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.SectorEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends CrudRepository<SectorEntity, Long> {
    List<SectorEntity> findAll();

    Optional<SectorEntity> findAllByNameEquals(String name);
}
