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

        @Query(value = "select wm from WaterMeterEntity wm where wm.client is null", countQuery = "select count(wm) from WaterMeterEntity wm where wm.client is null")
        Page<WaterMeterEntity> findAllWhereClientIsNull(Pageable pageable);

        Page<WaterMeterEntity> findBySerial(String serial, Pageable pageable);

        @Query(value = "select new com.hardnets.coop.model.dto.pageable.record.RecordDto(wm.id, wm.serial, cl.fullName, cl.clientNumber, wm.diameter, new com.hardnets.coop.model.dto.SectorDTO(wm.sector.id, wm.sector.name), c.reading) "
                        +
                        "from WaterMeterEntity wm " +
                        "inner join wm.client cl " +
                        "left join wm.consumptions c on c.period.id = :period " +
                        "where (:number is null or wm.serial = :number) and (:dni is null or cl.dni = :dni) " +
                        "and (:sector is null or wm.sector.name = :sector)", countQuery = "select count(wm) " +
                                        "from WaterMeterEntity wm " +
                                        "inner join wm.client cl " +
                                        "left join wm.consumptions c on c.period.id = :period " +
                                        "where (:number is null or wm.serial = :number) and (:dni is null or cl.dni = :dni) "
                                        +
                                        "and (:sector is null or wm.sector.name = :sector)")
        Page<RecordDto> findAllByCustomFilters(@Param("number") String number,
                        @Param("dni") String dni,
                        @Param("sector") String sector,
                        @Param("period") Long period,
                        Pageable pageable);

        @Query(value = "select new com.hardnets.coop.model.dto.pageable.record.RecordDto(wm.id, wm.serial, cl.fullName, cl.clientNumber, wm.diameter, new com.hardnets.coop.model.dto.SectorDTO(wm.sector.id, wm.sector.name), c.reading) "
                        +
                        "from WaterMeterEntity wm " +
                        "inner join wm.client cl " +
                        "left join wm.consumptions c on c.period.id = :period " +
                        "where (:number is null or wm.serial = :number) and (:dni is null or cl.dni = :dni) " +
                        "and (:sector is null or wm.sector.name = :sector) and (c.id is null or c.reading = 0)", countQuery = "select count(wm) "
                                        +
                                        "from WaterMeterEntity wm " +
                                        "inner join wm.client cl " +
                                        "left join wm.consumptions c on c.period.id = :period " +
                                        "where (:number is null or wm.serial = :number) and (:dni is null or cl.dni = :dni) "
                                        +
                                        "and (:sector is null or wm.sector.name = :sector) and (c.id is null or c.reading = 0)")
        Page<RecordDto> findAllByPendingCustomFilters(@Param("number") String number,
                        @Param("dni") String dni,
                        @Param("sector") String sector,
                        @Param("period") Long period,
                        Pageable pageable);

        @Query(value = "select new com.hardnets.coop.model.dto.pageable.record.RecordDto(wm.id, wm.serial, cl.fullName, cl.clientNumber, wm.diameter, new com.hardnets.coop.model.dto.SectorDTO(wm.sector.id, wm.sector.name), c.reading) "
                        +
                        "from WaterMeterEntity wm " +
                        "inner join wm.client cl " +
                        "left join wm.consumptions c on c.period.id = :period " +
                        "where (:number is null or wm.serial = :number) and (:dni is null or cl.dni = :dni) " +
                        "and (:sector is null or wm.sector.name = :sector) and (c.reading > 0)", countQuery = "select count(wm) "
                                        +
                                        "from WaterMeterEntity wm " +
                                        "inner join wm.client cl " +
                                        "left join wm.consumptions c on c.period.id = :period " +
                                        "where (:number is null or wm.serial = :number) and (:dni is null or cl.dni = :dni) "
                                        +
                                        "and (:sector is null or wm.sector.name = :sector) and (c.reading > 0)")
        Page<RecordDto> findAllByNoPendingCustomFilters(@Param("number") String number,
                        @Param("dni") String dni,
                        @Param("sector") String sector,
                        @Param("period") Long period,
                        Pageable pageable);

}
