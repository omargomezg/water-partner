package com.hardnets.coop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hardnets.coop.model.dto.ClientDTO;
import com.hardnets.coop.model.entity.ClientEntity;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, String> {

	Optional<ClientEntity> findByDni(String dni);

	@Query(value = "select c from ClientEntity c where (:dni is null or c.dni = :dni) and "
			+ "(:fullName is null or lower(c.fullName) like %:fullName%)", countQuery = "select count(c) from ClientEntity c where (:dni is null or c.dni = :dni) and :fullName is null or lower(c.fullName) like %:fullName%")
	Page<ClientEntity> findAllClientsByDniOrNameOrNone(@Param("dni") String dni, @Param("fullName") String fullName,
			Pageable pageable);

	@Query("select c from ClientEntity c where c.fullName like %:fullName%")
	List<ClientDTO> findAllClientsByName(String fullName);

}
