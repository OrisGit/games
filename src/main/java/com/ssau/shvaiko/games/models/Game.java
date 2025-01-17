package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ssau.shvaiko.games.dto.GameDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "game")
@Data
@NoArgsConstructor
@ToString(exclude = {"genres", "developer", "platforms", "publishers"})
@EqualsAndHashCode(exclude = {"genres", "developer", "platforms", "publishers"})
public class Game {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private Date release;
    private int pegiRate;
    @Column(columnDefinition="TEXT")
    private String description;
    private int criticsScore;
    private int personalScore;

    @ManyToOne
    private Developer developer;

    @ManyToMany
    private Set<Genre> genres;

    @ManyToMany
    private Set<Platform> platforms;

    @ManyToMany
    private Set<Publisher> publishers;

    public Game(GameDTO game) {
        this.name = game.getName();
        this.release = game.getRelease();
        this.pegiRate = game.getPegiRate();
        this.description = game.getDescription();
        this.criticsScore = game.getCriticsScore();
        this.personalScore = game.getPersonalScore();
    }

    public void update(Game game) {
        this.name = game.getName();
        this.release = game.getRelease();
        this.pegiRate = game.getPegiRate();
        this.description = game.getDescription();
        this.criticsScore = game.getCriticsScore();
        this.personalScore = game.getPersonalScore();
    }
}
