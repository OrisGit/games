package com.ssau.shvaiko.games.security.repo;


import com.ssau.shvaiko.games.security.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("prod")
public interface UserRepo extends JpaRepository<User, UUID> {
    Optional<User> findOneByUsername(String username);
}
