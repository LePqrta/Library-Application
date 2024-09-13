package com.CRUDApplication.security;

import com.CRUDApplication.model.Roles;
import com.CRUDApplication.model.User;
import com.CRUDApplication.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return new org.springframework.security.core.userdetails.User
                (user.getUsername(), user.getPassword(),mapRolesToAuthorities(Collections.singletonList(user.getRole())));
        }
    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
    }
    }

