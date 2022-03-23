package com.customer_service.customer_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CUSTOMER_NOT_FOUND(BAD_REQUEST, "System error"),
    INVALID_INPUT(BAD_REQUEST,"Invalid input"),
    CANNOT_BE_NULL(INTERNAL_SERVER_ERROR, "System error"),
    SERVER_ERROR(INTERNAL_SERVER_ERROR,"something went wrong at the server"),
    CUSTOMER_VALIDATION_ERROR(BAD_REQUEST, "Validation error"),
    ENTITY_NOT_FOUND(NOT_FOUND, "Entity not found"),
    ADDRESS_ALREADY_EXISTS(BAD_REQUEST,"That address already exists"),
    ADDRESS_NOT_FOUND(INTERNAL_SERVER_ERROR,"Address not found");
    private final HttpStatus httpStatus;
    private final String message;
}
