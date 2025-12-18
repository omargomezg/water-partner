package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ConflictException;
import com.hardnets.coop.exception.HandleException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.exception.WaterMeterNotFoundException;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.constant.StatusEnum;
import com.hardnets.coop.model.dto.MetersAvailableDto;
import com.hardnets.coop.model.dto.WaterMeterDTO;
import com.hardnets.coop.model.dto.WaterMeterFilterRequest;
import com.hardnets.coop.model.dto.pageable.record.RecordDto;
import com.hardnets.coop.model.dto.pageable.record.RecordsDto;
import com.hardnets.coop.model.dto.response.RelatedWaterMetersDto;
import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.model.entity.PeriodEntity;
import com.hardnets.coop.model.entity.SubsidyEntity;
import com.hardnets.coop.model.entity.TariffEntity;
import com.hardnets.coop.model.entity.WaterMeterEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.repository.PeriodRepository;
import com.hardnets.coop.repository.SubsidyRepository;
import com.hardnets.coop.repository.TariffRepository;
import com.hardnets.coop.repository.WaterMeterPageableRepository;
import com.hardnets.coop.repository.WaterMeterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@AllArgsConstructor
@Service
public class WaterMeterService {

	private final WaterMeterRepository waterMeterRepository;
	private final WaterMeterPageableRepository waterMeterPageableRepository;
	private final ClientRepository clientRepository;
	private final SubsidyRepository subsidyRepository;
	private final TariffRepository tariffRepository;
	private final PeriodRepository periodRepository;
	private final ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

	public void update(List<WaterMeterDTO> waterMeterDtos) {
		List<WaterMeterEntity> entities = new ArrayList<>();
		for (WaterMeterDTO dto : waterMeterDtos) {
			WaterMeterEntity entity = waterMeterRepository.findBySerial(dto.getSerial()).orElseThrow(
					() -> new WaterMeterNotFoundException("Water meter number " + dto.getSerial() + " was not found"));
			ClientEntity client = clientRepository.findByDni(dto.getDni()).orElseThrow(UserNotFoundException::new);
			entity.setClient(client);
			entities.add(entity);
		}
		waterMeterRepository.saveAll(entities);
	}

	public WaterMeterDTO update(WaterMeterDTO waterMeterDto) {
		var waterMeter = waterMeterRepository.findById(waterMeterDto.getId())
				.orElseThrow(WaterMeterNotFoundException::new);
		waterMeter.setDiameter(waterMeterDto.getDiameter());
		waterMeter.setTrademark(waterMeterDto.getTrademark());
		waterMeter.setSector(waterMeterDto.getSector());
		waterMeter.setUpdatedAt(new Date());
		waterMeter.setDescription(waterMeterDto.getComment());
		return modelMapper.map(waterMeterRepository.save(waterMeter), WaterMeterDTO.class);
	}

	public WaterMeterEntity create(WaterMeterEntity waterMeter) {
		return waterMeterRepository.saveAndFlush(waterMeter);
	}

	public WaterMeterDTO create(WaterMeterDTO waterMeterDto) {
		if (checkIfExistsSerial(waterMeterDto.getSerial())) {
			throw new ConflictException("El numero de serie ya existe");
		}
		WaterMeterEntity waterMeter = new WaterMeterEntity();
		waterMeter.setDescription(waterMeterDto.getComment());
		waterMeter.setSerial(waterMeterDto.getSerial());
		waterMeter.setTrademark(waterMeterDto.getTrademark());
		waterMeter.setSector(waterMeterDto.getSector());
		waterMeter.setDiameter(waterMeterDto.getDiameter());
		waterMeter.setCreatedAt(new Date());
		waterMeter.setStatus(StatusEnum.NEW);
		return new WaterMeterDTO(waterMeterRepository.save(waterMeter));
	}

	@Transactional
	public void delete(Long id) {
		var meter = waterMeterRepository.findById(id);
		if (meter.isPresent()) {
			waterMeterRepository.delete(meter.get());
			log.info("Meter with {} deleted", id);
		}
	}

	public boolean existSerial(Integer serial) {
		return waterMeterRepository.findBySerial(serial).isPresent();
	}

	public WaterMeterEntity getBySerial(Integer serial) {
		return waterMeterRepository.findBySerial(serial).orElseThrow(WaterMeterNotFoundException::new);
	}

	public WaterMeterEntity getById(Long id) {
		return waterMeterRepository.findById(id).orElseThrow(WaterMeterNotFoundException::new);
	}

	public MetersAvailableDto findAllWhereNotRelated(Integer pageIndex, Integer pageSize) {
		MetersAvailableDto meters = new MetersAvailableDto();
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		var result = waterMeterPageableRepository.findAllWhereClientIsNull(pageable);

		meters.getMeters().addAll(result.getContent().stream().map(meter -> modelMapper.map(meter, WaterMeterDTO.class))
				.toList());
		meters.setTotalHits(result.getTotalElements());
		return meters;
	}

    public List<WaterMeterEntity> getAllByPage(WaterMeterFilterRequest filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<WaterMeterEntity> cq = cb.createQuery(WaterMeterEntity.class);
        var root = cq.from(WaterMeterEntity.class);
        var predicates = buildPredicates(filter, cb, root);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }
        cq.orderBy(cb.desc(root.get("updatedAt")));
        var query = em.createQuery(cq);
        query.setFirstResult(filter.getPage() * filter.getSize());
        query.setMaxResults(filter.getSize());
        return query.getResultList();
	}

    public Long getTotalOfElements(WaterMeterFilterRequest filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<WaterMeterEntity> root = cq.from(WaterMeterEntity.class);
        List<Predicate> predicates = buildPredicates(filter, cb, root);
        if (!predicates.isEmpty()) {
            cq.where(predicates.toArray(new Predicate[0]));
        }
        cq.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));
        return em.createQuery(cq).getSingleResult();
    }

	public List<WaterMeterDTO> getAll() {
		List<WaterMeterEntity> waterMeterEntities = waterMeterRepository.findAll();
		return waterMeterEntities.stream().map(WaterMeterDTO::new)
				.sorted(Comparator.comparing(WaterMeterDTO::getUpdatedAt).reversed()).collect(Collectors.toList());
	}

	public Collection<RelatedWaterMetersDto> getByUser(String dni) {
		Collection<RelatedWaterMetersDto> relatedMeters = new HashSet<>();
		ClientEntity client = clientRepository.findByDni(dni).orElseThrow(UserNotFoundException::new);
		Collection<WaterMeterEntity> dbRelatedMeters = waterMeterRepository.findAllByClientOrderByUpdatedAtDesc(client);
		for (WaterMeterEntity dbRelatedMeter : dbRelatedMeters) {
			Optional<SubsidyEntity> subsidy = subsidyRepository
					.findAllByWaterMeterAndIsActiveAndEndingDateAfter(dbRelatedMeter.getId(), true, new Date());
			Optional<TariffEntity> tariff = tariffRepository.findBySizeAndClientType(dbRelatedMeter.getDiameter(),
					client.getClientType());
			RelatedWaterMetersDto related = new RelatedWaterMetersDto(dbRelatedMeter.getId(),
					dbRelatedMeter.getSerial(), dbRelatedMeter.getDiameter(), dbRelatedMeter.getCreatedAt(),
					dbRelatedMeter.getSector(), tariff.isPresent() ? tariff.get().getFlatFee() : 0,
					tariff.isPresent() ? tariff.get().getCubicMeter() : 0,
					subsidy.isPresent() ? subsidy.get().getPercentage() : 0);
			relatedMeters.add(related);
		}
		return relatedMeters.stream().sorted(Comparator.comparing(RelatedWaterMetersDto::getDischargeDate))
				.collect(Collectors.toList());
	}

	public void relateToClient(WaterMeterDTO waterMeterDto, String dni) {
		Integer serial = waterMeterDto.getSerial();
		ClientEntity client = clientRepository.findByDni(dni).orElseThrow(UserNotFoundException::new);
		waterMeterRepository.findBySerial(waterMeterDto.getSerial()).ifPresent(result -> {
			if (result.getClient() != null) {
				throw new HandleException("Water meter already related to user " + result.getClient().getFullName());
			}
		});
		WaterMeterEntity wEntity = new WaterMeterEntity();
		wEntity.setSerial(serial);
		wEntity.setTrademark(waterMeterDto.getTrademark());
		wEntity.setCreatedAt(new Date());
		wEntity.setSector(waterMeterDto.getSector());
		wEntity.setStatus(StatusEnum.NEW);
		wEntity.setDiameter(waterMeterDto.getDiameter());
		wEntity.setDescription(waterMeterDto.getComment());
		wEntity.setClient(client);
		waterMeterRepository.save(wEntity);
	}

	public RecordsDto findAllForSetConsumption(String number, String dni, String sector, String status,
			Integer pageIndex, Integer pageSize, String sortColumn, String direction) {
		RecordsDto recordsDto = new RecordsDto();
		PeriodEntity periodEntity = periodRepository.findByStatus(PeriodStatusEnum.ACTIVE).stream().findFirst()
				.orElseThrow();
		Pageable pageable = PageRequest.of(pageIndex, pageSize,
				direction.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, getColumnToSort(sortColumn));
		Page<RecordDto> consumptions;
		Integer serial = number == null ? null : Integer.parseInt(number);
		switch (status.toLowerCase()) {
		case "pending":
			consumptions = waterMeterPageableRepository.findAllByPendingCustomFilters(serial, dni, sector,
					periodEntity.getId(), pageable);
			break;
		case "no-pending":
			consumptions = waterMeterPageableRepository.findAllByNoPendingCustomFilters(serial, dni, sector,
					periodEntity.getId(), pageable);
			break;
		default:
			consumptions = waterMeterPageableRepository.findAllByCustomFilters(serial, dni, sector,
					periodEntity.getId(), pageable);
		}
		recordsDto.setRecords(consumptions.getContent());
		recordsDto.setTotalHits(consumptions.getTotalElements());
		return recordsDto;
	}

	private String getColumnToSort(String column) {
		switch (column) {
		case "client":
			column = "waterMeter.client.fullName";
			break;
		case "sector":
			column = "waterMeter.sector";
			break;
		default:
			column = "waterMeter.serial";
		}
		return column;
	}

	private boolean checkIfExistsSerial(Integer serial) {
		return waterMeterRepository.findBySerial(serial).isPresent();
	}

    private List<Predicate> buildPredicates(WaterMeterFilterRequest filter, CriteriaBuilder cb, Root<WaterMeterEntity> root) {
        List<Predicate> predicates = new ArrayList<>();
        if (filter.getSerial() != null) {
            predicates.add(cb.equal(root.get("serial"), filter.getSerial()));
        }
        return predicates;
    }

}
