package com.hardnets.coop.model.entity;

import com.hardnets.coop.model.constant.ProfileEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity extends PersonEntity implements UserDetails, Serializable {

    @Column
    private String password;

    @Enumerated
    private ProfileEnum role;

    @Column
    private Date lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
