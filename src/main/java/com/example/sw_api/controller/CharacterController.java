package com.example.sw_api.controller;

import com.example.sw_api.controller.dto.CharacterPageDetailsDto;
import com.example.sw_api.model.Character;
import com.example.sw_api.service.CharacterService;
import com.example.sw_api.service.DataFetcherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.sw_api.service.CharacterService.PAGE_SIZE;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    private final CharacterService characterService;
    private final DataFetcherService dataFetcherService;

    public CharacterController(CharacterService characterService, DataFetcherService dataFetcherService) {
        this.characterService = characterService;
        this.dataFetcherService = dataFetcherService;
    }

    @GetMapping
    public ResponseEntity<CharacterPageDetailsDto> getAllCharactersWithPaging(@RequestParam int page) {
        int pageNumber = page > 1 ? page : 1;
        List<Character> characters = characterService.findAllWithPaging(pageNumber - 1);
        int databaseSize = characterService.findAll().size();
        CharacterPageDetailsDto characterPageDetailsDto = CharacterPageDetailsDto.builder()
                .count(databaseSize)
                .pages((int) Math.ceil((double) databaseSize / PAGE_SIZE))
                .elements(characters)
                .build();
        return new ResponseEntity<>(characterPageDetailsDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Character> getCharacterById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(characterService.findById(id), HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<Character> addCharacter(@RequestBody Character character) {
        return new ResponseEntity<>(characterService.save(character), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Character> updateCharacter(@RequestBody Character character) {
        return new ResponseEntity<>(characterService.save(character), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCharacterById(@PathVariable("id") Long id) {
        characterService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteAllCharacter() {
        characterService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/fetchDataFromSwApi")
    public void fetchDataFromSwApi() {
        dataFetcherService.getData();
    }
}
