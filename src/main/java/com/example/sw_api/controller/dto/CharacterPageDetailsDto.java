package com.example.sw_api.controller.dto;

import com.example.sw_api.model.Character;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class CharacterPageDetailsDto {
    private int count;
    private int pages;
    private List<Character> elements;
}
