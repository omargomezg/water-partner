package com.hardnets.coop.convert;

import com.hardnets.coop.model.constant.SalesDocumentTypeEnum;
import com.hardnets.coop.model.dto.response.PendingPaymentDto;
import com.hardnets.coop.model.entity.BillEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class BillEntityToPendingDto implements Converter<BillEntity, PendingPaymentDto> {

    @Override
    public PendingPaymentDto convert(BillEntity billEntity) {
        return PendingPaymentDto.builder()
                .documentNumber(billEntity.getDocumentNumber())
                .documentType(SalesDocumentTypeEnum.BILL.name())
                .build();
    }
}
