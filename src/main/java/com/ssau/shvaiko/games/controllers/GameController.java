package com.ssau.shvaiko.games.controllers;

import com.ssau.shvaiko.games.models.Game;
import com.ssau.shvaiko.games.repositories.GameRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.ssau.shvaiko.games.config.AppConstants.API_V1;

@RequestMapping(API_V1 + "/games")
public class GameController extends DefaultController<Game> {

    protected GameController(GameRepository repository) {
        super(repository);
    }
}
