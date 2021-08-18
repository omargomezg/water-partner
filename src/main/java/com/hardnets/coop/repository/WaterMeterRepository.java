package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.dto.WaterMetersConsumptionDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeterEntity, Long> {

    Optional<WaterMeterEntity> findByNumber(String number);

    Collection<WaterMeterEntity> findAllByClientOrderByUpdatedDesc(ClientEntity clientEntity);

    @Query("select w.number from WaterMeterEntity w where w.client.rut = ?1")
    Collection<String> finadAllIdsByClient(String rut);

    @Query("select new com.hardnets.coop.model.dto.WaterMeterDto(wm.id, wm.number, wm.trademark, wm.size.id, wm.description, wm.sector, wm.updated)" +
            " from WaterMeterEntity wm where wm.client is null")
    Collection<WaterMeterDto> findAllWhereClientIsNull();

    @Query("select new com.hardnets.coop.model.dto.WaterMetersConsumptionDto(wm.id, wm.number, si.value, " +
            "wm.created, wm.sector, '', c.consumption) " +
            "from ConsumptionEntity c inner join c.waterMeter wm inner join wm.size si inner join wm.client cl " +
            "where c.period.id = :period and (:number is null or wm.number = :number) and (:rut is null or cl.rut = :rut) " +
            "and (:pending is false or c.consumption = 0) and (:sector is null or wm.sector = :sector)")
    Collection<WaterMetersConsumptionDto> findAllByCustomFilters(@Param("number") String number,
                                                                 @Param("rut") String rut,
                                                                 @Param("sector") String sector,
                                                                 @Param("period") Long period,
                                                                 @Param("pending") Boolean isPending);
}
