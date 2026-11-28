package com.zee.courseoauth2.service;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @dev : Ezekiel Eromosei
 * @date : 28 Nov, 2026
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .authorities("coder")
                .build();
    }
}
