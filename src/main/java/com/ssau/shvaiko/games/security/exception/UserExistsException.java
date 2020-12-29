package com.ssau.shvaiko.games.security.exception;

public class UserExistsException extends Exception {

    public UserExistsException(String message) {
        super(message);
    }

    public static UserExistsException withName(String name) {
        return new UserExistsException("User with name " + name + " already exist");
    }

    public static UserExistsException withId(String id) {
        return new UserExistsException("User with uuid " + id + " already exist");
    }
}
