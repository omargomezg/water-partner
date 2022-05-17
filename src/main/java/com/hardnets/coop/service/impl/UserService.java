package com.hardnets.coop.service.impl;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.UserException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.model.constant.ProfileEnum;
import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.UserRepository;
import com.hardnets.coop.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserDto signup(UserDto userDto, String password) {
        return null;
    }

    @Transactional
    public UserDto update(UserDto userDto) {
        var user = userRepository.findById(userDto.getDni()).orElseThrow(ClientNotFoundException::new);
        if (userDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        user.setNames(userDto.getNames());
        user.setLastName(userDto.getLastName());
        user.setMiddleName(userDto.getMiddleName());
        user.setEnabled(userDto.getEnabled());
        user.setProfile(ProfileEnum.valueOf(userDto.getRole().toUpperCase()));
        userRepository.save(user);
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    public Collection<UserDto> getUsers() {
        Collection<UserEntity> dbUsers = userRepository.findAllByProfileNot(ProfileEnum.KAL_EL);
        return dbUsers.stream().map(UserDto::new).collect(Collectors.toList());
    }

    public UserDto getByDni(String dni) throws UserNotFoundException {
        UserEntity user = userRepository.findById(dni).orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserDto(user);
    }

    @Transactional
    public UserDto create(CreateUserDto userDto) {
        if (Objects.isNull(userDto.getPassword())) {
            throw new UserException("Incomplete user data");
        }
        UserEntity user = new UserEntity();
        user.setDni(userDto.getDni());
        user.setNames(userDto.getNames());
        user.setMiddleName(userDto.getMiddleName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setProfile(ProfileEnum.valueOf(userDto.getRole().toUpperCase()));
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public void updatePassword(String dni, String password) {
        var user = userRepository.getById(dni);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}
