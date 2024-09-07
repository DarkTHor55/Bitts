package com.darkthor.Configuration;

import com.darkthor.Exceptions.EmailNotFoundException;
import com.darkthor.Service.Impl.UserServiceImpl;
import com.darkthor.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userService.getUserByEmail(email);
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
    }
}