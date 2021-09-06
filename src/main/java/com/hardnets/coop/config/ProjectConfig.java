package com.hardnets.coop.config;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.MeasureEnum;
import com.hardnets.coop.model.constant.ProfileEnum;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class ProjectConfig {

    private static final String PROFILE = "PROFILE";
    private static final String CLIENT_TYPE = "CLIENT_TYPE";
    private static final String WATER_METER_SIZE = "WATER_METER_SIZE";
    private final UserRepository userRepository;
    private final DropDownListRepository dropDownListRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void checkStartup() {
        log.info("Inicia validación de data");
        checkProfiles();
        checkClientType();
        checkWaterMeterMeasures();
        checkAdministrator();
        log.info("Fin validación de data");
    }

    private void checkWaterMeterMeasures() {
        if (dropDownListRepository.findByCode(MeasureEnum.THIRTEEN.label).isEmpty()) {
            saveDropDownEntity(MeasureEnum.THIRTEEN.label, MeasureEnum.THIRTEEN.label, WATER_METER_SIZE);
        }
        if (dropDownListRepository.findByCode(MeasureEnum.NINETEEN.label).isEmpty()) {
            saveDropDownEntity(MeasureEnum.NINETEEN.label, MeasureEnum.NINETEEN.label, WATER_METER_SIZE);
        }
        if (dropDownListRepository.findByCode(MeasureEnum.TWENTY_FIVE.label).isEmpty()) {
            saveDropDownEntity(MeasureEnum.TWENTY_FIVE.label, MeasureEnum.TWENTY_FIVE.label, WATER_METER_SIZE);
        }
        dropDownListRepository.findAllByDropDownListType(WATER_METER_SIZE)
                .forEach(item -> log.info("Measure created: {}", item.getCode()));
    }

    private void checkAdministrator() {
        if (userRepository.findAll().isEmpty()) {
            Optional<DropDownListEntity> role = dropDownListRepository.findByCode(ProfileEnum.ADMINISTRATOR.label);
            if (role.isPresent()) {
                UserEntity user = new UserEntity();
                user.setEmail("omar.fdo.gomez@gmail.com");
                user.setNames("Omar Fernando");
                user.setMiddleName("Gómez");
                user.setLastName("Gómez");
                user.setRut("140812269");
                user.setPassword(passwordEncoder.encode("samsungMac"));
                user.setRole(role.get());
                userRepository.save(user);
                log.info("User {} created", user.getEmail());
            }
        }
    }

    private void checkProfiles() {
        if (dropDownListRepository.findByCode(ProfileEnum.ADMINISTRATOR.label).isEmpty()) {
            saveDropDownEntity("Administrador", ProfileEnum.ADMINISTRATOR.label, PROFILE);
        }
        if (dropDownListRepository.findByCode(ProfileEnum.FINANCE.label).isEmpty()) {
            saveDropDownEntity("Finanzas", ProfileEnum.FINANCE.label, PROFILE);
        }
        if (dropDownListRepository.findByCode(ProfileEnum.RAISING.label).isEmpty()) {
            saveDropDownEntity("Recaudación", ProfileEnum.RAISING.label, PROFILE);
        }
        if (dropDownListRepository.findByCode(ProfileEnum.BILLING.label).isEmpty()) {
            saveDropDownEntity("Tarificador", ProfileEnum.BILLING.label, PROFILE);
        }
        dropDownListRepository.findAllByDropDownListType(PROFILE).forEach(item -> log.info("Profile created: {} {}", item.getValue(), item.getCode()));
    }

    private void checkClientType(){
        if (dropDownListRepository.findByCode(ClientTypeEnum.PARTNER.label).isEmpty()) {
            saveDropDownEntity("Socio", ClientTypeEnum.PARTNER.label, CLIENT_TYPE);
        }
        if (dropDownListRepository.findByCode(ClientTypeEnum.PUBLIC.label).isEmpty()) {
            saveDropDownEntity("Publico", ClientTypeEnum.PUBLIC.label, CLIENT_TYPE);
        }
        if (dropDownListRepository.findByCode(ClientTypeEnum.PRIVATE.label).isEmpty()) {
            saveDropDownEntity("Privado", ClientTypeEnum.PRIVATE.label, CLIENT_TYPE);
        }
        dropDownListRepository.findAllByDropDownListType(CLIENT_TYPE).forEach(item -> log.info("Client type created: {} {}",
                item.getValue(), item.getCode()));
    }

    private void saveDropDownEntity(String value, String code, String dropType) {
        DropDownListEntity dropDownListEntity = new DropDownListEntity();
        dropDownListEntity.setDropDownListType(dropType);
        dropDownListEntity.setItems(new ArrayList<>());
        dropDownListEntity.setCode(code);
        dropDownListEntity.setValue(value);
        dropDownListRepository.save(dropDownListEntity);
    }
}
