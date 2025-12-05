package com.hardnets.coop.model.dto;

import lombok.Data;

@Data
public class PageRequest {
    private Integer page = 0;
    private Integer size = 0;
}
