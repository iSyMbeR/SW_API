package com.example.sw_api.exception;

public class UnrecognizedException extends RuntimeException {

    public UnrecognizedException() {
        super("Something went wrong");
    }

    public UnrecognizedException(String message) {
        super("Something went wrong" + message);
    }
}
