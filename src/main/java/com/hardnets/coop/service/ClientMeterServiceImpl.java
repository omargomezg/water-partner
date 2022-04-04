package com.hardnets.coop.service;

import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientMeterServiceImpl implements ClientMeterService {

    private final ClientRepository clientRepository;
    private final WaterMeterRepository waterMeterRepository;

    @Override
    public void delete(Long meter, String dni) {
        var meterEntity = waterMeterRepository.findAllIdsByClient(dni).stream()
                .filter(element-> element.getId().equals(meter))
                .findFirst()
                .orElseThrow(WaterMeterNotFoundException::new);
        meterEntity.setClient(null);
        waterMeterRepository.save(meterEntity);
    }
}
