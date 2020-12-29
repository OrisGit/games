package com.ssau.shvaiko.games.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table
@Data
@NoArgsConstructor
@ToString(exclude = {"games"})
@EqualsAndHashCode
public class Developer implements Updatable<Developer>{

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String headquarters;
    private Date founded;

    @JsonIgnore
    @OneToMany(mappedBy = "developer")
    private List<Game> games;

    public void update(Developer developer) {
        this.name = developer.name;
        this.headquarters = developer.headquarters;
        this.founded = developer.founded;
    }
}
