package com.busbooking.model.dto;

import com.busbooking.model.User;
import com.busbooking.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDto implements UserDetails {

    private String userName;
    private String password;
    private List<GrantedAuthority> authorities;

    public UserDto(User user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        // Split roles by comma and map them to GrantedAuthority
        this.authorities = Arrays.stream(
                        (user.getRoles() != null ? user.getRoles() : Role.USER.name()).split(","))
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return true;
    }
}