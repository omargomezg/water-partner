package com.hardnets.coop.model.dto.values;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClientTypeDto {
    private String name;
    private String translateEs;
}
