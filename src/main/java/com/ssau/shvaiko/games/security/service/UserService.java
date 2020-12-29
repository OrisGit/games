package com.ssau.shvaiko.games.security.service;

import com.ssau.shvaiko.games.security.entity.User;
import com.ssau.shvaiko.games.security.exception.UserExistsException;

public interface UserService {
    User saveUser(User user) throws UserExistsException;
}
