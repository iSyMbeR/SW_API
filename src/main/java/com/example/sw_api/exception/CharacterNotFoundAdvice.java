package com.example.sw_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CharacterNotFoundAdvice {

    @ExceptionHandler(CharacterNotFoundException.class)
    public ResponseEntity<String> characterNotFoundHandler(CharacterNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
