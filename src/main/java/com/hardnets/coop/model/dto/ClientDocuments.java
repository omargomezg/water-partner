package com.hardnets.coop.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ClientDocuments {
    private Integer id;
    private Date emmit = new Date();
    private Integer amount;
    private String status;
}
