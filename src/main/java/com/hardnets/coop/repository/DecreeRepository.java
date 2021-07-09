package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.DecreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DecreeRepository extends JpaRepository<DecreeEntity, Long> {

    Optional<DecreeEntity> findFirstByNumberEquals(Integer number);
}
