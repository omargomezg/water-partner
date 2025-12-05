package com.hardnets.coop.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;

@Repository
public interface WaterMeterRepository extends JpaRepository<WaterMeterEntity, Long> {

	Optional<WaterMeterEntity> findBySerial(Integer serial);

	@Query(value = "select distinct wm.diameter from WaterMeterEntity wm")
	List<DiameterEnum> findAllDiameters();

	Collection<WaterMeterEntity> findAllByClientOrderByUpdatedAtDesc(ClientEntity clientEntity);

	@Query("select w from WaterMeterEntity w where w.client.dni = ?1")
	Collection<WaterMeterEntity> findAllIdsByClient(String dni);

	@Query("select wm from WaterMeterEntity wm where wm.client is null")
	Collection<WaterMeterDTO> findAllWhereClientIsNull();

	void deleteAllById(Long id);
}
