package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.AttributeKeyEnum;
import com.hardnets.coop.model.entity.CompanyEntity;
import com.hardnets.coop.model.entity.GenericAttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@Repository
public interface GenericAttributeRepository extends JpaRepository<GenericAttributeEntity, Long> {

    @Query("select ga from GenericAttributeEntity ga inner join ga.company c where c.id = :id and ga.key = :key")
    Optional<GenericAttributeEntity> findByCompanyIdAndAttributeKey(@Param("id") Long companyId, @Param("key") AttributeKeyEnum key);

    List<GenericAttributeEntity> findAllByCompany(CompanyEntity company);

}
