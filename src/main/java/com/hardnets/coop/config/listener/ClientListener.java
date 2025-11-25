package com.hardnets.coop.config.listener;

import com.hardnets.coop.model.entity.ClientEntity;
import com.hardnets.coop.repository.ClientRepository;
import com.hardnets.coop.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Listener para actualizar el nombre completo del cliente (fullName).
 * Evaluar eliminar mas adelante
 *
 * @author ogomez
 * @version 1.0.0
 */
@Log4j2
@AllArgsConstructor
@Component
public class ClientListener {

    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void checkUsers() {
        var clientToUpdate = clientService.findAll().stream()
                .filter(this::fullNameEmpty)
                .map(this::putFullName)
                .collect(Collectors.toList());
        if (!clientToUpdate.isEmpty()) {
            clientRepository.saveAll(clientToUpdate);
            log.info("Se actualizaron {} clientes", clientToUpdate.size());
        }
    }

    private ClientEntity putFullName(ClientEntity client) {
        client.setFullName(buildFullName(client));
        return client;
    }

    private boolean fullNameEmpty(ClientEntity client) {
        return client.getFullName() == null;
    }

    public String buildFullName(ClientEntity client) {
        //TODO Evaluar si se mantiene esta logica
            /*return ClientTypeEnum.isPartner(client.getClientType()) ?
                String.format("%s %s %s", client.getNames(), client.getMiddleName(), client.getLastName()) :
                client.getBusinessName();*/
        return client.getNames();
    }
}
