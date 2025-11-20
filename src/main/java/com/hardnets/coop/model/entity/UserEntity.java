package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.ProfileEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends PersonEntity implements UserDetails {

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
