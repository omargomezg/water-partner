package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.WaterMeterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterMeterPageableRepository extends PagingAndSortingRepository<WaterMeterEntity, Long> {

    @Query(value = "select wm from WaterMeterEntity wm where wm.client is null",
            countQuery = "select count(wm) from WaterMeterEntity wm where wm.client is null")
    Page<WaterMeterEntity> findAllWhereClientIsNull(Pageable pageable);
}
