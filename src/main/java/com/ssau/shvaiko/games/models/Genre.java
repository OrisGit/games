package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "genre")
@Data
@NoArgsConstructor
@ToString(exclude = {"games"})
@EqualsAndHashCode(exclude = {"games"})
public class Genre implements Updatable<Genre>{

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column(columnDefinition="TEXT")
    private String description;

    @JsonIgnore
    @ManyToMany(mappedBy = "genres")
    private Set<Game> games;

    public void update(Genre genre) {
        this.name = genre.name;
        this.description = genre.description;
    }
}
