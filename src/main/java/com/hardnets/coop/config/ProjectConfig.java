package com.hardnets.coop.config;

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
    private final UserRepository userRepository;
    private final DropDownListRepository dropDownListRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void checkStartup() {
        log.info("Inicia validación de data");
        checkProfiles();
        checkAdministrator();
        log.info("Fin validación de data");

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
            createProfile("Administrador", ProfileEnum.ADMINISTRATOR, PROFILE);
        }
        if (dropDownListRepository.findByCode(ProfileEnum.FINANCE.label).isEmpty()) {
            createProfile("Finanzas", ProfileEnum.FINANCE, PROFILE);
        }
        if (dropDownListRepository.findByCode(ProfileEnum.RAISING.label).isEmpty()) {
            createProfile("Recaudación", ProfileEnum.RAISING, PROFILE);
        }
        if (dropDownListRepository.findByCode(ProfileEnum.BILLING.label).isEmpty()) {
            createProfile("Tarificador", ProfileEnum.BILLING, PROFILE);
        }
        dropDownListRepository.findAllByDropDownListType(PROFILE).forEach(item -> log.info("Profile created: {} {}", item.getValue(), item.getCode()));
    }

    private void createProfile(String value, ProfileEnum profile, String dropType) {
        DropDownListEntity dropDownListEntity = new DropDownListEntity();
        dropDownListEntity.setDropDownListType(dropType);
        dropDownListEntity.setItems(new ArrayList<>());
        dropDownListEntity.setCode(profile.label);
        dropDownListEntity.setValue(value);
        dropDownListRepository.save(dropDownListEntity);
    }
}
