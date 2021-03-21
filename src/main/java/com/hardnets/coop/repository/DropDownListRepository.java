package com.hardnets.coop.repository;

import com.hardnets.coop.entity.DropDownListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DropDownListRepository extends JpaRepository<DropDownListEntity, Long> {
    Optional<DropDownListEntity> findByCode(String code);
}
