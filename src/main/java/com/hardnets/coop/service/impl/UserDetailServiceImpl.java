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
import com.hardnets.coop.model.dto.UserDTO;
import com.hardnets.coop.model.dto.UserFilterRequest;
import com.hardnets.coop.model.entity.UserEntity;
import com.hardnets.coop.repository.UserRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public UserDTO update(@NotNull UserDTO userDto) {
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
		return modelMapper.map(userRepository.save(user), UserDTO.class);
	}

	public List<UserEntity> findAll(UserFilterRequest filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<UserEntity> cq = cb.createQuery(UserEntity.class);
		var root = cq.from(UserEntity.class);
		List<Predicate> predicates = buildPredicates(filter, cb, root);
		cq.where(predicates.toArray(new Predicate[0]));
		return em.createQuery(cq).getResultList();
	}

	public long count(UserFilterRequest filter) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		var root = cq.from(UserEntity.class);
		List<Predicate> predicates = buildPredicates(filter, cb, root);
		cq.select(cb.count(root)).where(predicates.toArray(new Predicate[0]));
		return em.createQuery(cq).getSingleResult();
	}

	public Collection<UserDTO> getUsers() {
		Collection<UserEntity> dbUsers = userRepository.findAllByProfilesNot(ProfileEnum.KAL_EL);
		return dbUsers.stream().map(UserDTO::new).collect(Collectors.toList());
	}

	public UserDTO getByDni(@NonNull String dni) throws UserNotFoundException {
		UserEntity user = userRepository.findById(dni).orElseThrow(UserNotFoundException::new);
		return new UserDTO(user);
	}

	@Transactional
	public UserDTO create(CreateUserDto userDto) {
		if (Objects.isNull(userDto.getPassword())) {
			throw new UserException("Incomplete user data");
		}
		UserEntity user = new UserEntity();
		user.setDni(userDto.getDni());
		user.setFullName(userDto.getFullName());
		user.setEmail(userDto.getEmail());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setProfiles(List.of(ProfileEnum.valueOf(userDto.getRole().toUpperCase())));
		return modelMapper.map(userRepository.save(user), UserDTO.class);
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

	private List<Predicate> buildPredicates(UserFilterRequest filter, CriteriaBuilder cb, Root<UserEntity> root) {
		List<Predicate> predicates = new ArrayList<>();
		if (filter.getDni() != null && !filter.getDni().isEmpty()) {
			predicates.add(cb.equal(root.get("dni"), filter.getDni()));
		}
		if (filter.getFullName() != null && !filter.getFullName().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("fullName")), "%" + filter.getFullName().toLowerCase() + "%"));
		}
		if (filter.getEmail() != null && !filter.getEmail().isEmpty()) {
			predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
		}

		return predicates;
	}

}
