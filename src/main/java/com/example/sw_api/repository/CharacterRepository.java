package com.example.sw_api.repository;

import com.example.sw_api.model.Character;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    @Query("select c from Character as c")
    List<Character> findAllCharacters(Pageable pageable);

}
