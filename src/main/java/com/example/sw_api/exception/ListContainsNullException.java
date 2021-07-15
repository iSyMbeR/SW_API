package com.example.sw_api.exception;

public class ListContainsNullException extends RuntimeException{
    public ListContainsNullException() {
        super("The list contains null");
    }
}
