package com.devilpanda.user_service.adapter.rest;

import com.devilpanda.user_service.app.api.UserAlreadyExistsException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@ControllerAdvice(basePackageClasses = RestExceptionHandler.class)
@Order(HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    private ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
