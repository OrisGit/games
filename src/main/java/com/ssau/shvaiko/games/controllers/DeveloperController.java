package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Developer;
import com.ssau.shvaiko.games.repositories.DeveloperRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/developers")
@CrossOrigin
@RestController
public class DeveloperController extends DefaultController<Developer> {

    protected DeveloperController(DeveloperRepository repository) {
        super(repository, Developer.class);
    }
}
