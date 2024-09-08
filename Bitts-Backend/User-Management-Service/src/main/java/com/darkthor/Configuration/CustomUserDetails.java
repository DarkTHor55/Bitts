package com.darkthor.Configuration;

import com.darkthor.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    // Constructor
    public CustomUserDetails(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        this.email = user.getEmail();
        this.password = user.getPassword();
        // Example role; replace with actual role logic if needed
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
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
        return email;
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
