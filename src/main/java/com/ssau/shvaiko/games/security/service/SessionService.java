package com.ssau.shvaiko.games.security.service;

import com.ssau.shvaiko.games.security.entity.User;

public interface SessionService {
    User getCurrentUser();
}
