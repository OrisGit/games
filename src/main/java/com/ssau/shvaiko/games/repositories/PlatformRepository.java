package com.ssau.shvaiko.games.repositories;

import com.ssau.shvaiko.games.models.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, UUID> {
}
