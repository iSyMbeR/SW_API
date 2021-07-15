package com.example.sw_api.exception;

import com.example.sw_api.model.Character;

public class CharacterNotFoundException extends RuntimeException {

    public CharacterNotFoundException(Long id) {
        super("Character with id " + id + " not found");
    }

    public CharacterNotFoundException(Character character) {
        super("Character " + character.getName() + " not found");
    }
}
