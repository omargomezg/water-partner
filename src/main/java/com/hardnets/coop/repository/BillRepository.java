package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.SalesDocumentStatusEnum;
import com.hardnets.coop.model.entity.BillEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends PagingAndSortingRepository<BillEntity, Long> {
    Page<BillEntity> getAllByClient_Dni(String dni, Pageable pageable);

    Page<BillEntity> getAllByStatusAndClient_DniOrderByDateOfEmissionAsc(SalesDocumentStatusEnum status, String dni,
                                                                         Pageable pageable);

}
