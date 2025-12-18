package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ResourceExistsException;
import com.hardnets.coop.exception.TariffNotFoundException;
import com.hardnets.coop.model.constant.DiameterEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.TariffDto;
import com.hardnets.coop.model.dto.TariffFilterRequest;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import com.hardnets.coop.service.TariffService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;
    private final WaterMeterRepository waterMeterRepository;
    private final ClientTypeRepository clientTypeRepository;;
    private final ConversionService conversionService;
    private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    @Override
    public TariffDto findById(Long id) {
        TariffEntity tariff = tariffRepository.findById(id)
                .orElseThrow(() -> new TariffNotFoundException("Tariff not exists"));
        return conversionService.convert(tariff, TariffDto.class);
    }

    @Override
    public List<TariffEntity> getAll(TariffFilterRequest filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TariffEntity> cq = cb.createQuery(TariffEntity.class);
        var root = cq.from(TariffEntity.class);
        var predicates = buildPredicates(filter);
        if (predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }
        cq.orderBy(cb.desc(root.get("lastUpdate")));
        var query = em.createQuery(cq);
        query.setFirstResult(filter.getPage() * filter.getSize());
        query.setMaxResults(filter.getSize());
        return query.getResultList();
        /*List<AllTariffsDto> allTariffs = new ArrayList<>();
        AllTariffsBaseDto baseTariff = new AllTariffsBaseDto();
        tariffRepository.findAll().forEach(tariffEntity -> allTariffs.add(new AllTariffsDto(tariffEntity)));
        var tariffs = allTariffs.stream().sorted(Comparator.comparing(AllTariffsDto::getLastUpdate).reversed())
                .collect(Collectors.toList());
        baseTariff.setTariffs(tariffs);
        baseTariff.setAllRatesAreConfigured(hasTariffForAllDiameters());
        return baseTariff;*/
    }

    @Override
    public TariffDto create(TariffDto tariffDto) {
        if (existsTariff(tariffDto))
            throw new ResourceExistsException("No puedes crear esta tarifa, ya existe una similar.");
        var clientType = clientTypeRepository.findById(tariffDto.getClientType().getId()).orElseThrow();
        var tariff = modelMapper.map(tariffDto, TariffEntity.class);
        tariff.setClientType(clientType);
        var result = tariffRepository.save(tariff);
        return modelMapper.map(result, TariffDto.class);
    }

    @Override
    public TariffEntity update(TariffDto tariffDto) {
        TariffEntity dbTariff = tariffRepository.findById(tariffDto.getId()).orElseThrow(
                () -> new TariffNotFoundException(String.format("Tariff not with id %s found", tariffDto.getId())));
        var clientType = clientTypeRepository.findById(tariffDto.getClientType().getId())
                .orElseThrow(() -> new TariffNotFoundException("Client type not found"));
        dbTariff.setCubicMeter(tariffDto.getCubicMeter());
        dbTariff.setClientType(clientType);
        dbTariff.setFlatFee(tariffDto.getFlatFee());
        dbTariff.setLastUpdate(Instant.now());
        dbTariff.setDiameter(tariffDto.getDiameter());
        dbTariff.setStatus(StatusEnum.valueOf(tariffDto.getStatus()));
        return tariffRepository.save(dbTariff);
    }

    @Override
    public boolean hasTariffForAllDiameters() {
        var hastTariff = true;
        var diameters = waterMeterRepository.findAllDiameters();
        for (DiameterEnum diameter : diameters) {
            if (tariffRepository.findFirstByDiameter(diameter).isEmpty()) {
                hastTariff = false;
            }
        }
        return hastTariff;
    }

    private boolean existsTariff(TariffDto tariff) {
        var clientType = clientTypeRepository.findById(tariff.getClientType().getId())
                .orElseThrow(() -> new TariffNotFoundException("Client type not found"));
        return tariffRepository.findBySizeAndClientType(tariff.getDiameter(), clientType).isPresent();
    }

    private List<Predicate> buildPredicates(TariffFilterRequest filter) {
        return new ArrayList<>();
    }
}
