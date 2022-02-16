package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.WaterMeterDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeterEntity, Long> {

    Optional<WaterMeterEntity> findBySerial(Integer serial);

    @Query(value = "select distinct wm.diameter from WaterMeterEntity wm")
    List<DiameterEnum> findAllDiameters();

    Collection<WaterMeterEntity> findAllByClientOrderByUpdatedDesc(ClientEntity clientEntity);

    @Query("select w from WaterMeterEntity w where w.client.rut = ?1")
    Collection<WaterMeterEntity> findAllIdsByClient(String rut);

    @Query("select new com.hardnets.coop.model.dto.WaterMeterDto(wm.id, wm.serial, wm.trademark, wm.diameter, wm.description, wm.sector, wm.updated)" +
            " from WaterMeterEntity wm where wm.client is null")
    Collection<WaterMeterDto> findAllWhereClientIsNull();
}
