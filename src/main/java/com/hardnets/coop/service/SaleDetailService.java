package com.hardnets.coop.service;

import com.hardnets.coop.model.entity.ConsumptionEntity;

import java.util.List;

public interface SaleDetailService<T> {

    List<T> getDetail(ConsumptionEntity consumption, Long documentId);
}
