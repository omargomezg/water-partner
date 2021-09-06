package com.hardnets.coop.model.dto.municipal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MunicipalReportDto {
    private List<ReportDto> detail= new ArrayList<>();
    private Long totalHits = 0L;
}
