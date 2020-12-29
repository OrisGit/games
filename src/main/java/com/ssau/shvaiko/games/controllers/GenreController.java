package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Genre;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/genres")
@Setter(onMethod = @__(@Autowired))
@CrossOrigin
@RestController
public class GenreController extends DefaultController<Genre> {
    protected GenreController(JpaRepository<Genre, UUID> repository) {
        super(repository, Genre.class);
    }
}

