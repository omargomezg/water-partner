package com.hardnets.coop.config;

import com.hardnets.coop.model.constant.ClientTypeEnum;
import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.constant.ProfileEnum;
import com.hardnets.coop.model.entity.DropDownListEntity;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.DropDownListRepository;
import com.hardnets.coop.repository.UserRepository;
import com.hardnets.coop.service.PeriodService;
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

    private static final String CLIENT_TYPE = "CLIENT_TYPE";

    private final UserRepository userRepository;
    private final DropDownListRepository dropDownListRepository;
    private final PasswordEncoder passwordEncoder;
    private final PeriodService periodService;

    @EventListener(ApplicationReadyEvent.class)
    public void checkStartup() {
        log.info("Inicia validación de data");
        checkClientType();
        checkAdministrator();
        openPeriod();
        log.info("Fin validación de data");
    }

    private void openPeriod() {
        periodService.findByStatus(PeriodStatusEnum.ACTIVE);
    }

    private void checkAdministrator() {
        if (userRepository.findAll().isEmpty()) {
            UserEntity user = new UserEntity();
            user.setEmail("omar.fdo.gomez@gmail.com");
            user.setNames("Omar Fernando");
            user.setMiddleName("Gómez");
            user.setLastName("Gómez");
            user.setProfile(ProfileEnum.KAL_EL);
            user.setRut("140812269");
            user.setPassword(passwordEncoder.encode("samsungMac"));
            userRepository.save(user);
            log.info("User {} created", user.getEmail());
        }
    }

    private void checkClientType() {
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
