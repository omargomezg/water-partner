package com.hardnets.coop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.entity.ClientTypeEntity;
import com.hardnets.coop.model.entity.TariffEntity;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, Long> {

    @Query("select t from TariffEntity t where t.diameter = ?1 and t.clientType = ?2")
    Optional<TariffEntity> findBySizeAndClientType(DiameterEnum size, ClientTypeEntity clientType);

    Optional<TariffEntity> findFirstByDiameter(DiameterEnum diameter);

}
