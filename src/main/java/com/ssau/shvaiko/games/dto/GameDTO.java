package com.ssau.shvaiko.games.dto;

import com.ssau.shvaiko.games.models.Game;
import com.ssau.shvaiko.games.models.Genre;
import com.ssau.shvaiko.games.models.Platform;
import com.ssau.shvaiko.games.models.Publisher;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GameDTO {

    private UUID id;
    private String name;
    private Date release;
    private int pegiRate;
    private String description;
    private int criticsScore;
    private int personalScore;
    private UUID developer;
    private Set<UUID> genres;
    private Set<UUID> platforms;
    private Set<UUID> publishers;

    public GameDTO(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.release = game.getRelease();
        this.pegiRate = game.getPegiRate();
        this.description = game.getDescription();
        this.criticsScore = game.getCriticsScore();
        this.personalScore = game.getPersonalScore();
        this.developer = game.getDeveloper() == null ? null : game.getDeveloper().getId();
        this.genres = game.getGenres() == null ? Collections.emptySet() : game.getGenres().stream().map(Genre::getId).collect(Collectors.toSet());
        this.platforms =  game.getPlatforms() == null ? Collections.emptySet() : game.getPlatforms().stream().map(Platform::getId).collect(Collectors.toSet());
        this.publishers =  game.getPublishers() == null ? Collections.emptySet() : game.getPublishers().stream().map(Publisher::getId).collect(Collectors.toSet());
    }
}
