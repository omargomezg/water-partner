package com.hardnets.coop.repository;

import com.hardnets.coop.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    @Query("select us from UserEntity us where us.rut = :rut")
    Optional<UserEntity> findByRut(@Param("rut") String rut);

    Optional<UserEntity> findByEmail(String email);

    @Query("select us from UserEntity us where us.rut = :rut or us.email = :email")
    List<UserEntity> findByRutOrEmail(@Param("rut") String rut, @Param("email") String email);

}
