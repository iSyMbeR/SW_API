package com.example.sw_api.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String height;
    private String mass;

    private String hairColor;
    private String eyeColor;
    private String birthYear;
    private String gender;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            }
    )

    private HomeWorld homeworld;
    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "character_starhip",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "starship_id")
    )
    private Set<Starship> starships;
}
