package com.hardnets.coop.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hardnets.coop.exception.ClientNotFoundException;
import com.hardnets.coop.exception.UserException;
import com.hardnets.coop.exception.UserNotFoundException;
import com.hardnets.coop.model.constant.ProfileEnum;
import com.hardnets.coop.model.dto.CreateUserDto;
import com.hardnets.coop.model.dto.UserDto;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final ModelMapper modelMapper;

	@Transactional
	public UserDto update(@NotNull UserDto userDto) {
		var dni = userDto.getDni();
		if (Objects.isNull(dni)) {
			throw new UserException("DNI is required");
		}
		var user = userRepository.findById(dni).orElseThrow(ClientNotFoundException::new);
		if (userDto.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		user.setFullName(userDto.getFullName());
		user.setEnabled(userDto.getEnabled());
		List<ProfileEnum> roles = new ArrayList<>();
		userDto.getRoles().forEach(role -> roles.add(ProfileEnum.valueOf(role.toUpperCase())));
		user.setProfiles(roles);
		userRepository.save(user);
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	public Collection<UserDto> getUsers() {
		Collection<UserEntity> dbUsers = userRepository.findAllByProfilesNot(ProfileEnum.KAL_EL);
		return dbUsers.stream().map(UserDto::new).collect(Collectors.toList());
	}

	public UserDto getByDni(@NonNull String dni) throws UserNotFoundException {
		UserEntity user = userRepository.findById(dni).orElseThrow(UserNotFoundException::new);
		return new UserDto(user);
	}

	@Transactional
	public UserDto create(CreateUserDto userDto) {
		if (Objects.isNull(userDto.getPassword())) {
			throw new UserException("Incomplete user data");
		}
		UserEntity user = new UserEntity();
		user.setDni(userDto.getDni());
		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setProfiles(List.of(ProfileEnum.valueOf(userDto.getRole().toUpperCase())));
		return modelMapper.map(userRepository.save(user), UserDto.class);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.info("Loading UserDetails for {}", email);
		var user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
		log.info("UserDetails loaded for {}", user);
		return user;
	}

	public void updatePassword(@NonNull String dni, String password) {
		var user = userRepository.findById(dni).orElseThrow(UserNotFoundException::new);
		user.setPassword(passwordEncoder.encode(password));
		userRepository.save(user);
	}
}
