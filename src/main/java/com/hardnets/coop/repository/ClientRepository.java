package com.hardnets.coop.repository;

import com.hardnets.coop.model.dto.ClientDto;
import com.hardnets.coop.model.entity.ClientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClientRepository extends PagingAndSortingRepository<ClientEntity, String> {

    Optional<ClientEntity> findByDni(String dni);

    @Query(value = "select c " +
            "from ClientEntity c where (:dni is null or c.dni = :dni) and " +
            "(:name is null or (lower(c.names) like concat('%', concat(:name, '%')) or " +
            "                   lower(c.lastName) like :name or " +
            "                   lower(c.middleName) like :name or " +
            "                   lower(c.businessName) like concat('%', concat(:name, '%')))" +
            ")",
            countQuery = "select count(c) " +
                    "from ClientEntity c where (:dni is null or c.dni = :dni) and " +
                    "(:name is null or (lower(c.names) like concat('%', concat(:name, '%')) or " +
                    "                   lower(c.lastName) like :name or " +
                    "                   lower(c.middleName) like :name or " +
                    "                   lower(c.businessName) like concat('%', concat(:name, '%')))" +
                    ")")
    Page<ClientEntity> findAllClientsByDniOrNameOrNone(@Param("dni") String dni, @Param("name") String name,
                                                       Pageable pageable);

    @Query("select new com.hardnets.coop.model.dto.ClientDto(c.dni, c.names, c.middleName, c.lastName, c.businessName, c.clientType) " +
            "from ClientEntity c where c.names like %:name% or c.lastName like %:name% or c.middleName like %:name% or c.businessName like %:name%")
    List<ClientDto> findAllClientsByName(String name);

}
