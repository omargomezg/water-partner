package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.DropDownListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DropDownListRepository extends JpaRepository<DropDownListEntity, Long> {
    Optional<DropDownListEntity> findByCode(String code);

    List<DropDownListEntity> findAllByDropDownListType(String type);
}
