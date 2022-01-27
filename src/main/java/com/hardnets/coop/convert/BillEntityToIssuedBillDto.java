package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.SalesDocumentTypeEnum;
import com.hardnets.coop.model.dto.issuedBills.IssuedBillDto;
import com.hardnets.coop.model.entity.BillEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillEntityToIssuedBillDto implements Converter<BillEntity, IssuedBillDto> {

    @Override
    public IssuedBillDto convert(BillEntity billEntity) {
        return IssuedBillDto.builder()
                .dateOfEmission(billEntity.getDateOfEmission())
                .documentNumber(billEntity.getDocumentNumber())
                .status(billEntity.getStatus().name())
                .documentType(SalesDocumentTypeEnum.BILL.name())
                .build();
    }
}
