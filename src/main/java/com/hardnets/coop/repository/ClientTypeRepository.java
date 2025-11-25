package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.ClientTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientTypeEntity, Long> {
}
