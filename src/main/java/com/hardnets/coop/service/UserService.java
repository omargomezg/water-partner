package com.hardnets.coop.service;

import com.hardnets.coop.dto.CreateUserDto;
import com.hardnets.coop.dto.UserDto;
import com.hardnets.coop.entity.DropDownListEntity;
import com.hardnets.coop.entity.UserEntity;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.repository.DropDownListRepository;
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

@RequiredArgsConstructor
@Log4j2
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final DropDownListRepository downListRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto signup(UserDto userDto, String password) {
        return null;
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        Optional<UserEntity> user = userRepository.findById(userDto.getRut());
        user.ifPresent(userEntity -> userRepository.save(userEntity));
        return userDto;
    }

    public Collection<UserDto> getUsers() {
        Collection<UserEntity> dbUsers = (Collection<UserEntity>) userRepository.findAll();
        return dbUsers.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserDto getByRut(String rut) throws UserNotFoundException {
        UserEntity user = userRepository.findById(rut).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserDto(user);
    }

    @Transactional
    public UserDto create(CreateUserDto userDto) throws Exception {
        Optional<DropDownListEntity> role = downListRepository.findById(userDto.getRoleId());
        log.info("finded this role: {}", role.get());
        UserEntity user = new UserEntity();
        user.setRut(userDto.getRut());
        user.setNames(userDto.getNames());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role.get());
        UserEntity dbUser = userRepository.save(user);
        return new UserDto(dbUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
