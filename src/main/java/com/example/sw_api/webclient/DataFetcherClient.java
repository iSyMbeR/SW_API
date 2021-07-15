package com.example.sw_api.webclient;

import com.example.sw_api.model.Character;
import com.example.sw_api.model.HomeWorld;
import com.example.sw_api.model.Starship;
import com.example.sw_api.service.CharacterService;
import com.example.sw_api.webclient.dto.StarWarsCharacterDto;
import com.example.sw_api.webclient.dto.StarWarsCountDto;
import com.example.sw_api.webclient.dto.StarWarsHomeWorldDto;
import com.example.sw_api.webclient.dto.StarWarsStarshipDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataFetcherClient {
    Logger logger = LoggerFactory.getLogger(DataFetcherClient.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final CharacterService characterService;
    private static final String API_URL = "https://swapi.dev/api/";
    private final int numberOfCharacters = getNumberOfCharacters();
    private int id;

    public void fetchData() {
        id = 0;
        if (characterService.findAll().size() >= numberOfCharacters) {
            return;
        }
        List<Character> fetchedCharacters = new ArrayList<>();
        while (fetchedCharacters.size() != numberOfCharacters) {
            try {
                Character character = getCharacterData();
                if (character != null) {
                    fetchedCharacters.add(character);
                }
            } catch (HttpClientErrorException e) {
                logger.info("Endpoint with id " + id + " has no body");
            } catch (Exception e) {
                System.out.println("Error unrecognized " + e.getMessage());
            }
        }
        characterService.saveAll(fetchedCharacters);
    }

    private Character getCharacterData() throws NullPointerException {
        StarWarsCharacterDto dto;
        try {
            dto = restTemplate.getForObject(API_URL + "people/{id}/", StarWarsCharacterDto.class, id++);
        } catch (NullPointerException e) {
            logger.warn("Empty response in getCharacterData method");
            return null;
        }

        Character character = null;
        try {
            character = Character.builder()
                    .name(dto.getName())
                    .gender(dto.getGender())
                    .height(dto.getHeight())
                    .mass(dto.getMass())
                    .hairColor(dto.getHair_color())
                    .eyeColor(dto.getEye_color())
                    .birthYear(dto.getBirth_year())
                    .homeworld(getHomeWorldData(dto))
                    .starships(getStarshipData(dto))
                    .build();
        } catch (NullPointerException e) {
            logger.warn("NullPointerException when creating an character object, in getCharacterData method");
        } catch (Exception e) {
            logger.warn("Exception when creating an character object" + e.getMessage());
        }
        return character;
    }

    private HomeWorld getHomeWorldData(StarWarsCharacterDto dto) throws NullPointerException {
        StarWarsHomeWorldDto homeWorldDto;
        try {
            homeWorldDto = restTemplate.getForObject(dto.getHomeworld(), StarWarsHomeWorldDto.class);
        } catch (NullPointerException e) {
            logger.warn("Empty response in getHomeWorldData method");
            return null;
        } catch (Exception e) {
            logger.warn("getHomeWorldData method " + e.getMessage());
            return null;
        }

        HomeWorld homeWorld;
        try {
            homeWorld = HomeWorld.builder()
                    .terrain(homeWorldDto.getTerrain())
                    .surfaceWater(homeWorldDto.getSurface_water())
                    .rotationPeriod(homeWorldDto.getRotation_period())
                    .population(homeWorldDto.getPopulation())
                    .orbitalPeriod(homeWorldDto.getOrbital_period())
                    .gravity(homeWorldDto.getGravity())
                    .name(homeWorldDto.getName())
                    .diameter(homeWorldDto.getDiameter())
                    .climate(homeWorldDto.getClimate())
                    .build();
        } catch (NullPointerException e) {
            logger.warn("NullPointerException when creating an homeWorld object, in getHomeWorldData method");
            return null;
        } catch (Exception e) {
            logger.warn("Exception when creating an homeWorld object" + e.getMessage());
            return null;
        }
        return homeWorld;
    }

    private Set<Starship> getStarshipData(StarWarsCharacterDto dto) {
        Set<Starship> starships = new HashSet<>();
        if (!dto.getStarships().isEmpty()) {
            for (String s : dto.starships) {
                StarWarsStarshipDto starWarsStarshipDto = restTemplate.getForObject(s, StarWarsStarshipDto.class);
                starships.add(Starship.builder()
                        .cargoCapacity(starWarsStarshipDto.getCargo_capacity())
                        .consumables(starWarsStarshipDto.getConsumables())
                        .costInCredits(starWarsStarshipDto.getCost_in_credits())
                        .crew(starWarsStarshipDto.getCrew())
                        .hyperdriveRating(starWarsStarshipDto.getHyperdrive_rating())
                        .length(starWarsStarshipDto.getLength())
                        .manufacturer(starWarsStarshipDto.getManufacturer())
                        .model(starWarsStarshipDto.getModel())
                        .starshipClass(starWarsStarshipDto.getStarship_class())
                        .maxAtmosphericSpeed(starWarsStarshipDto.getMax_atmosphering_speed())
                        .mglt(starWarsStarshipDto.getMglt())
                        .name(starWarsStarshipDto.getName())
                        .passengers(starWarsStarshipDto.getPassengers())
                        .build());
            }
        }
        return starships;
    }

    private int getNumberOfCharacters() {
        StarWarsCountDto response;
        try {
            response = restTemplate.getForObject(API_URL + "people?page=1", StarWarsCountDto.class);
        } catch (NullPointerException e) {
            logger.warn("Empty response in getNumberOfCharacters method");
            return 0;
        } catch (Exception e) {
            logger.warn("getNumberOfCharacters method " + e.getMessage());
            return 0;
        }

        return response.getCount();
    }
}
