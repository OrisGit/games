package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/publishers")
@CrossOrigin
@RestController
public class PublisherController extends DefaultController<Publisher> {

    protected PublisherController(JpaRepository<Publisher, UUID> repository) {
        super(repository, Publisher.class);
    }
}
