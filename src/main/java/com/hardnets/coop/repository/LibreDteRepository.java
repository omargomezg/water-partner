package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.LibreDteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibreDteRepository extends JpaRepository<LibreDteEntity, Long> {
}
