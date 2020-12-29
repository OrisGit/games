package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/platforms")
public class PlatformController extends DefaultController<Platform> {
    protected PlatformController(JpaRepository<Platform, UUID> repository) {
        super(repository);
    }
}
