package com.hardnets.coop.model.dto.pageable.record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecordsDto {
    private List<RecordDto> records;
    private Long totalHits = 0L;
}
