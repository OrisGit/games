package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "publishers")
@Data
@NoArgsConstructor
@ToString(exclude = {"games"})
@EqualsAndHashCode(exclude = {"games"})
public class Publisher implements Updatable<Publisher>{

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String headquarters;
    private Date founded;

    @JsonIgnore
    @ManyToMany
    private Set<Game> games;

    public void update(Publisher publisher) {
        this.name = publisher.name;
        this.headquarters = publisher.headquarters;
        this.founded = publisher.founded;
    }
}
