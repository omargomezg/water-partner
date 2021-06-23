package com.hardnets.coop.service;

import com.hardnets.coop.exception.UserException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Omar Gómez - omar.fdo.gomez@gmail.com
 */
@RequiredArgsConstructor
@Log4j2
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto signup(UserDto userDto, String password) {
        return null;
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findById(userDto.getRut());
        user.ifPresent(userRepository::save);
        return userDto;
    }

    public Collection<UserDto> getUsers() {
        Collection<UserEntity> dbUsers = userRepository.findAll();
        return dbUsers.stream().map(this::newUserDto).collect(Collectors.toList());
    }

    public UserDto getByRut(String rut) throws UserNotFoundException {
        UserEntity user = userRepository.findByRut(rut)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return newUserDto(user);
    }

    @Transactional
    public UserDto create(CreateUserDto userDto) throws Exception {
        if (userRepository.findByRutOrEmail(userDto.getRut(), userDto.getEmail()).isEmpty()) {
            UserEntity user = new UserEntity(
                    passwordEncoder.encode(userDto.getPassword()),
                    userDto.getRole(),
                    null
            );
            user.setRut(userDto.getRut());
            user.setNames(userDto.getNames());
            user.setMiddleName(userDto.getMiddleName());
            user.setLastName(userDto.getLastName());
            user.setEmail(userDto.getEmail());
            UserEntity dbUser = userRepository.save(user);
            return newUserDto(dbUser);
        }
        throw new UserException("User rut or email already exists");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private UserDto newUserDto(UserEntity user) {
        return new UserDto(user.getRut(), user.getRole(), user.getNames(), user.getMiddleName(), user.getEmail(),
                user.getLastName(), user.getLastLogin(), user.getEnabled());
    }

}
