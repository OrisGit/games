package com.ssau.shvaiko.games.security.controller;

import com.ssau.shvaiko.games.security.dto.UserDTO;
import com.ssau.shvaiko.games.security.entity.User;
import com.ssau.shvaiko.games.security.exception.UserExistsException;
import com.ssau.shvaiko.games.security.service.SessionService;
import com.ssau.shvaiko.games.security.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ssau.shvaiko.games.security.config.SecurityConstants.AUTH_API_V1;
import static com.ssau.shvaiko.games.security.config.SecurityConstants.REGISTRATION_PATH;

@RestController
@CrossOrigin
@RequestMapping(AUTH_API_V1)
@Profile("prod")
@Setter(onMethod = @__(@Autowired))
public class AuthenticationController {

    private UserService userService;
    private SessionService sessionService;

    @GetMapping("/login")
    public UserDTO login() {
        User currentUser = sessionService.getCurrentUser();
        return new UserDTO(currentUser);
    }

    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<UserDTO> register(@RequestBody User user) throws UserExistsException {
        User newUser = userService.saveUser(user);
        return new ResponseEntity<>(new UserDTO(newUser), HttpStatus.CREATED);
    }

}
