package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.DropDownListEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListRepository extends CrudRepository<DropDownListEntity, Long> {
    Optional<List<DropDownListEntity>> findAllByDropDownListType(String type);
}
