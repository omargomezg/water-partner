package com.hardnets.coop.repository;

import com.hardnets.coop.dto.ClientDto;
import com.hardnets.coop.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Query("select new com.hardnets.coop.dto.ClientDto(c.rut, c.names, c.middleName, c.lastName, c.birthDate, c.dateOfAdmission, c.businessName, c.businessActivity, c.email, c.clientType.value, c.clientType.id, c.clientType.code, c.enabled) from ClientEntity c where c.rut = ?1")
    Optional<ClientDto> findUserDtoByRut(String rut);

    Optional<ClientEntity> findByRut(String rut);

    @Query("select new com.hardnets.coop.dto.ClientDto(c.rut, c.names, c.middleName, c.lastName, c.businessName, c.clientType.value, c.clientType.id, c.clientType.code) from ClientEntity c")
    Collection<ClientDto> findAllClientsDto();

}
