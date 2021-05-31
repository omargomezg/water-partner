package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findById(String rut);

    Optional<UserEntity> findByEmail(String email);

}
