package com.example.sw_api.webclient.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class StarWarsCharacterDto {
    public String name;
    public String height;
    public String mass;
    public String hair_color;
    public String skin_color;
    public String eye_color;
    public String birth_year;
    public String gender;
    public String homeworld;
    public Set<String> starships;
}
