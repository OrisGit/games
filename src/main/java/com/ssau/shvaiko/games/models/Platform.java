package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "platform")
@Data
@NoArgsConstructor
@ToString(exclude = {"games"})
@EqualsAndHashCode(exclude = {"games"})
public class Platform implements Updatable<Platform>{

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String developer;
    private String family;

    @JsonIgnore
    @ManyToMany
    private Set<Game> games;

    public void update(Platform platform) {
        this.name = platform.name;
        this.developer = platform.developer;
        this.family = platform.family;
    }
}
