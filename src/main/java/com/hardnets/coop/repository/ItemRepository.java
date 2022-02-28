package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.ItemEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends PagingAndSortingRepository<ItemEntity, Long> {

    List<ItemEntity> findAllByIsActive(Boolean active);
}
