package com.ssau.shvaiko.games.security.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID id;
    @Column(name = "username", nullable = false, unique = true)
    @NonNull
    private String username;
    @Column(name = "password", nullable = false)
    @NonNull
    private String password;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
}
