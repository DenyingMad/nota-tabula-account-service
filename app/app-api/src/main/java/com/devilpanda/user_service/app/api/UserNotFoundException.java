package com.devilpanda.user_service.app.api;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User not found with credentials -> %s";

    public UserNotFoundException(String credential) {
        super(String.format(MESSAGE, credential));
    }
}
