package com.hardnets.coop.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private DropDownListEntity role;

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
