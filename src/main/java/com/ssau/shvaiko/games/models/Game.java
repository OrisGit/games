package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssau.shvaiko.games.dto.GameDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Date release;
    private int pegiRate;
    private String description;
    private int criticsScore;
    private int personalScore;

    @ManyToOne
    private Developer developer;

    @JsonIgnore
    @ManyToMany(mappedBy = "games")
    private Set<Genre> genres;

    @JsonIgnore
    @ManyToMany(mappedBy = "games")
    private Set<Platform> platforms;

    @JsonIgnore
    @ManyToMany(mappedBy = "games")
    private Set<Publisher> publishers;

    public Game(GameDTO game) {
        this.name = game.getName();
        this.release = game.getRelease();
        this.pegiRate = game.getPegiRate();
        this.description = game.getDescription();
        this.criticsScore = game.getCriticsScore();
        this.personalScore = game.getPersonalScore();
    }

    public void update(GameDTO game) {
        this.name = game.getName();
        this.release = game.getRelease();
        this.pegiRate = game.getPegiRate();
        this.description = game.getDescription();
        this.criticsScore = game.getCriticsScore();
        this.personalScore = game.getPersonalScore();
    }
}
