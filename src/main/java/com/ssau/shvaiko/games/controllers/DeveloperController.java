package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Developer;
import com.ssau.shvaiko.games.repositories.DeveloperRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/developers")
public class DeveloperController extends DefaultController<Developer> {

    protected DeveloperController(DeveloperRepository repository) {
        super(repository);
    }
}
