package com.devilpanda.account_service.app.api;

public class UserAlreadyExistsException extends RuntimeException {
    private static final String MESSAGE = "%s is already taken";

    public UserAlreadyExistsException(String message) {
        super(String.format(MESSAGE, message));
    }
}
