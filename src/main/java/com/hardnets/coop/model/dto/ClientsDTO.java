package com.hardnets.coop.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class ClientsDTO {
    private Long totalHits;
    private List<ClientDTO> items;
}
