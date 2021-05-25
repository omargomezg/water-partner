package com.hardnets.coop.repository;

import com.hardnets.coop.dto.WaterMeterDto;
import com.hardnets.coop.entity.ClientEntity;
import com.hardnets.coop.entity.WaterMeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeterEntity, Long> {

    Optional<WaterMeterEntity> findByNumber(String number);

    @Query("select wm from WaterMeterEntity wm where wm.client.rut = ?1")
    Collection<WaterMeterEntity> findAllByClientRut(String rut);

    Collection<WaterMeterEntity> findAllByClientOrderByUpdatedDesc(ClientEntity clientEntity);

    @Query("select w.number from WaterMeterEntity w where w.client.rut = ?1")
    Collection<String> finadAllIdsByClient(String rut);

    @Query("select new com.hardnets.coop.dto.WaterMeterDto(wm.id, wm.number, wm.trademark, wm.size.id, wm.description, wm.sector, wm.updated)" +
            " from WaterMeterEntity wm where wm.client is null")
    Collection<WaterMeterDto> findAllWhereClientIsNull();

    @Query("select wm from WaterMeterEntity wm where wm.number = ?1 or wm.client.rut = ?2")
    Collection<WaterMeterEntity> findAllByNumberOrClient(String number, String rut);
}
