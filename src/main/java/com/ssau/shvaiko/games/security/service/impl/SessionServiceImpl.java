package com.ssau.shvaiko.games.security.service.impl;

import com.ssau.shvaiko.games.security.entity.User;
import com.ssau.shvaiko.games.security.repo.UserRepo;
import com.ssau.shvaiko.games.security.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod")
public class SessionServiceImpl implements SessionService {

    private final UserRepo userRepo;

    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        return userRepo.findOneByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username " + principal.getUsername() + " was not found"));
    }
}
