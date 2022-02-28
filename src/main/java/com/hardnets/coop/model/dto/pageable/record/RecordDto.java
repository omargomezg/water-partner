package com.hardnets.coop.model.dto.pageable.record;

import com.hardnets.coop.model.constant.DiameterEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class RecordDto {
    private Long id;
    private Integer serial;
    private String client;
    private Integer clientNumber;
    private DiameterEnum diameter;
    private String sector;
    private Integer reading;
}
