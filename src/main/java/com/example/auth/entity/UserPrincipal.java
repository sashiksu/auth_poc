package com.example.auth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER"));
    }

    @Override
    public String getPassword() {
        return user.getHashPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getIsExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getIsLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getIsCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getIsEnabled();
    }
}
