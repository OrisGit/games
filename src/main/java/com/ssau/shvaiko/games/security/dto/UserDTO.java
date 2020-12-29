package com.ssau.shvaiko.games.security.dto;

import com.ssau.shvaiko.games.security.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String name;
    private String email;

    public UserDTO(User user) {
        this.name = user.getUsername();
        this.email = user.getEmail();
    }
}
