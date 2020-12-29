package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
public class Game implements Updatable<Game>{

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
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany(mappedBy = "games")
    private List<Platform> platforms;

    @JsonIgnore
    @ManyToMany(mappedBy = "games")
    private List<Publisher> publishers;

    public void update(Game game) {
        this.name = game.name;
        this.release = game.release;
        this.pegiRate = game.pegiRate;
        this.description = game.description;
        this.criticsScore = game.criticsScore;
        this.personalScore = game.personalScore;
    }
}
