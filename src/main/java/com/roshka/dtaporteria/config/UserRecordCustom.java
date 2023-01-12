package com.roshka.dtaporteria.config;

import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import java.util.Collection;

public class UserRecordCustom  implements UserDetails {
    private final String uid;
    private final String email;
    private final boolean emailVerified;

    private final Collection<? extends GrantedAuthority> permisos;


    public UserRecordCustom(String uid, String email, boolean emailVerified,Collection<? extends GrantedAuthority> permisos ) {
        Assert.isTrue(email != null && !"".equals(email) && uid != null, "Cannot pass null or empty values to constructor");
        this.uid = uid;
        this.email = email;
        this.emailVerified = emailVerified;
        this.permisos = permisos;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permisos;
    }

    @Override
    public String getPassword() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return this.uid;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.emailVerified;
    }
}
