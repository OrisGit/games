package com.ssau.shvaiko.games.security.service.impl;

import com.ssau.shvaiko.games.security.entity.User;
import com.ssau.shvaiko.games.security.exception.UserExistsException;
import com.ssau.shvaiko.games.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Profile("prod & default_user")
public class EventListener {

    private final UserService userService;

    @org.springframework.context.event.EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() throws UserExistsException {
        userService.saveUser(User.builder().username("admin").email("admin").password("admin").build());
    }
}
