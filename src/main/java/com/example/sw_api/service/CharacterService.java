package com.example.sw_api.service;

import com.example.sw_api.exception.CharacterNotFoundException;
import com.example.sw_api.exception.ListContainsNullException;
import com.example.sw_api.exception.UnrecognizedException;
import com.example.sw_api.model.Character;
import com.example.sw_api.repository.CharacterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterService {
    public static final int PAGE_SIZE = 10;
    private final CharacterRepository characterRepository;
    Logger logger = LoggerFactory.getLogger(CharacterService.class);

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    public List<Character> findAllWithPaging(int page) {
        return characterRepository.findAllCharacters(PageRequest.of(page, PAGE_SIZE));
    }

    public Character findById(Long id) {
        return characterRepository.findById(id)
                .orElseThrow(() -> new CharacterNotFoundException(id));
    }

    public Character save(Character character) {
        if (character == null) {
            logger.warn("Trying to save null");
        } else {
            characterRepository.save(character);
            logger.info("Character has been added");
        }
        return character;
    }

    public void saveAll(List<Character> characters) {
        if (characters.contains(null)) {
            throw new ListContainsNullException();
        } else {
            characterRepository.saveAll(characters);
            logger.info("Characters have been added");
        }

    }

    public void delete(Character character) {
        try {
            characterRepository.delete(character);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Failed to delete character with id " + character.getId());
            throw new CharacterNotFoundException(character);
        } catch (Exception e) {
            logger.warn("Failed to delete character with id " + character.getId());
            throw new UnrecognizedException();
        }
    }

    public void deleteAll() {
        characterRepository.deleteAll();
    }

    public void deleteById(Long id) {
        try {
            characterRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Failed to delete character with id " + id);
            throw new CharacterNotFoundException(id);
        } catch (Exception e) {
            logger.warn("Failed to delete character with id " + id);
            throw new UnrecognizedException(e.getMessage());
        }
    }
}
