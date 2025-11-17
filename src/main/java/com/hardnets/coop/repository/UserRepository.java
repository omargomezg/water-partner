package com.hardnets.coop.repository;

import com.hardnets.coop.model.constant.ProfileEnum;
import com.hardnets.coop.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findAllByProfileNot(ProfileEnum profileEnum);

}
