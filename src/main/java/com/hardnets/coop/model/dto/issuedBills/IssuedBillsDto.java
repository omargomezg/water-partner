package com.hardnets.coop.model.dto.issuedBills;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssuedBillsDto {

    private Long totalHits = 0L;

    private List<IssuedBillDto> contents = new ArrayList<>();
}
