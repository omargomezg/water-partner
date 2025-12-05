package com.hardnets.coop.model.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hardnets.coop.model.constant.ProfileEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "users")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends PersonEntity implements UserDetails {

	private static final long serialVersionUID = 907261733412678574L;

	@Column(name = "profile")
	@Enumerated
	private List<ProfileEnum> profiles;

	@Column
	private Date lastLogin;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (profiles == null) {
			return Collections.emptyList();
		}
		return profiles.stream().map(profile -> new SimpleGrantedAuthority("ROLE_" + profile.name()))
				.collect(Collectors.toList());
	}

	@Override
	public String getUsername() {
		return getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return getEnabled();
	}

	@Override
	public boolean isAccountNonLocked() {
		return getEnabled();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return getEnabled();
	}

	@Override
	public boolean isEnabled() {
		return getEnabled();
	}
}
