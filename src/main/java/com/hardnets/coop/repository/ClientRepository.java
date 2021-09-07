package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Query("select new com.hardnets.coop.model.dto.ClientDto(c.rut, c.names, c.middleName, c.lastName, c.birthDate, " +
            "c.dateOfAdmission, c.businessName, c.businessActivity, c.telephone, c.email, c.clientType, c.enabled) from " +
            "ClientEntity c where c.rut = ?1")
    Optional<ClientDto> findUserDtoByRut(String rut);

    Optional<ClientEntity> findByRut(String rut);

    @Query("select new com.hardnets.coop.model.dto.ClientDto(c.rut, c.names, c.middleName, c.lastName, c.businessName, c.clientType) from ClientEntity c")
    List<ClientDto> findAllClientsDto();

    @Query("select new com.hardnets.coop.model.dto.ClientDto(c.rut, c.names, c.middleName, c.lastName, c" +
            ".businessName, c.clientType) " +
            "from ClientEntity c where (:rut is null or c.rut = :rut) and " +
            "(:name is null or (lower(c.names) like concat('%', concat(:name, '%')) or " +
            "                   lower(c.lastName) like :name or " +
            "                   lower(c.middleName) like :name or " +
            "                   lower(c.businessName) like concat('%', concat(:name, '%')))" +
            ")")
    List<ClientDto> findAllClientsByRutOrNameOrNone(@Param("rut") String rut, @Param("name") String name);

    @Query("select new com.hardnets.coop.model.dto.ClientDto(c.rut, c.names, c.middleName, c.lastName, c.businessName, c.clientType) " +
            "from ClientEntity c where c.names like %:name% or c.lastName like %:name% or c.middleName like %:name% or c.businessName like %:name%")
    List<ClientDto> findAllClientsByName(String name);

}
