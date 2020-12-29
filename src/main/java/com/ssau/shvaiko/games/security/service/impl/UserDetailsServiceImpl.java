package com.ssau.shvaiko.games.security.service.impl;

import com.ssau.shvaiko.games.security.entity.User;
import com.ssau.shvaiko.games.security.exception.UserExistsException;
import com.ssau.shvaiko.games.security.repo.UserRepo;
import com.ssau.shvaiko.games.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Primary
@Profile("prod")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserDetailsServiceImpl implements UserDetailsService, UserService {

    private static final List<SimpleGrantedAuthority> defaultAuthorities =
            Collections.singletonList(new SimpleGrantedAuthority("user"));
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findOneByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " was not found"));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), defaultAuthorities);
    }

    @Override
    public User saveUser(User user) throws UserExistsException {
        if (userRepo.findOneByUsername(user.getUsername()).isPresent()) {
            throw UserExistsException.withName(user.getUsername());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }
}
