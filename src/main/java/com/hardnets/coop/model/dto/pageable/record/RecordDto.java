package com.hardnets.coop.model.dto.pageable.record;

import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.dto.SectorDTO;

public record RecordDto (
     Long id,
     String serial,
     String client,
     Integer clientNumber,
     DiameterEnum diameter,
     SectorDTO sector,
     Integer reading
) {}
