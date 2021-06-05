package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.TariffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TariffRepository extends JpaRepository<TariffEntity, Long> {

    @Query("select t from TariffEntity t where t.size.id = ?1 and t.clientType.id = ?2")
    Optional<TariffEntity> findBySizeAndClientType(Long size, Long clientType);

}
