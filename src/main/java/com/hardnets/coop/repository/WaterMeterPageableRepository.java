package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.pageable.record.RecordDto;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterMeterPageableRepository extends PagingAndSortingRepository<WaterMeterEntity, Long> {

    @Query(value = "select wm from WaterMeterEntity wm where wm.client is null",
            countQuery = "select count(wm) from WaterMeterEntity wm where wm.client is null")
    Page<WaterMeterEntity> findAllWhereClientIsNull(Pageable pageable);

    Page<WaterMeterEntity> findBySerial(Integer serial, Pageable pageable);

    @Query(value = "select new com.hardnets.coop.model.dto.pageable.record.RecordDto(wm.id, wm.serial, cl.fullName, " +
            "wm.diameter, wm.sector, c.reading) " +
            "from ConsumptionEntity c inner join c.waterMeter wm inner join wm.client cl " +
            "where c.period.id = :period and (:number is null or wm.serial = :number) and (:rut is null or cl.rut = :rut) " +
            "and (:sector is null or wm.sector = :sector)",
            countQuery = "select count(c)" +
                    "from ConsumptionEntity c inner join c.waterMeter wm inner join wm.client cl " +
                    "where c.period.id = :period and (:number is null or wm.serial = :number) and (:rut is null or cl.rut = :rut) " +
                    "and (:sector is null or wm.sector = :sector)")
    Page<RecordDto> findAllByCustomFilters(@Param("number") Integer number,
                                           @Param("rut") String rut,
                                           @Param("sector") String sector,
                                           @Param("period") Long period,
                                           Pageable pageable);

}
