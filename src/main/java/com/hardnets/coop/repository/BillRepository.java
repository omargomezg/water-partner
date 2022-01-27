package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.entity.BillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Long> {
    List<BillEntity> getAllByClient_Rut(String rut);

    List<BillEntity> getAllByStatusAndClient_Rut(SalesDocumentStatusEnum status, String rut);

}
