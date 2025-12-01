package com.hardnets.coop.model.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hardnets.coop.model.constant.ProfileEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends PersonEntity implements UserDetails {

	private static final long serialVersionUID = 907261733412678574L;

	@Column(name = "profile")
	@Enumerated
	private List<ProfileEnum> profiles;

	@Column
	private Date lastLogin;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		var roles = new ArrayList<GrantedAuthority>();
		profiles.forEach(profileEnum -> roles.add(new SimpleGrantedAuthority(profileEnum.name())));
		return roles;
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
