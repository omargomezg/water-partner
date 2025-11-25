package com.hardnets.coop.config.listener;

import java.util.Date;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hardnets.coop.model.constant.PeriodStatusEnum;
import com.hardnets.coop.model.constant.ProfileEnum;
import com.hardnets.coop.model.entity.ClientTypeEntity;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.ClientTypeRepository;
import com.hardnets.coop.repository.UserRepository;
import com.hardnets.coop.service.PeriodService;
import com.hardnets.coop.service.impl.ConsumptionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Configuration
public class ProjectConfig {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PeriodService periodService;
    private final ConsumptionService consumptionService;
    private final ClientTypeRepository clientTypeRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void checkStartup() {
        log.info("Inicia validación de data");
        checkClientType();
        checkAdministrator();
        checkPeriod();
        log.info("Fin validación de data");
    }

    private void checkPeriod() {
        var period = periodService.findByStatus(PeriodStatusEnum.ACTIVE);
        if (period.isEmpty()) {
            var newPeriod = periodService.create(new Date());
            consumptionService.createAllRecords(newPeriod.getId());
        }
    }

    private void checkAdministrator() {
        if (userRepository.findAll().isEmpty()) {
            UserEntity user = new UserEntity();
            user.setEmail("omar.fdo.gomez@gmail.com");
            user.setNames("Omar Fernando");
            user.setMiddleName("Gómez");
            user.setLastName("Gómez");
            user.setProfiles(List.of(ProfileEnum.KAL_EL));
            user.setDni("140812269");
            user.setPassword(passwordEncoder.encode("samsungMac"));
            userRepository.save(user);
            log.info("Super User {} created", user.getEmail());
        }
    }

    private void checkClientType() {
        if (clientTypeRepository.findAll().isEmpty()) {
            ClientTypeEntity residentPartner = new ClientTypeEntity();
            residentPartner.setDescription("Es socio residente");
            residentPartner.setActive(true);
            clientTypeRepository.save(residentPartner);

            ClientTypeEntity noResidentPartner = new ClientTypeEntity();
            noResidentPartner.setDescription("Es socio No residente");
            noResidentPartner.setActive(true);
            clientTypeRepository.save(noResidentPartner);

            ClientTypeEntity publicClient = new ClientTypeEntity();
            publicClient.setDescription("Publico");
            publicClient.setActive(true);
            clientTypeRepository.save(publicClient);

            ClientTypeEntity privateClient = new ClientTypeEntity();
            privateClient.setDescription("Privado");
            privateClient.setActive(true);
            clientTypeRepository.save(privateClient);
        }
    }
}
