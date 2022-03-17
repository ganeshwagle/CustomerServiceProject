package com.customer_service.customer_service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CUSTOMER_NOT_FOUND(INTERNAL_SERVER_ERROR, "System error"),
    CANNOT_BE_NULL(INTERNAL_SERVER_ERROR, "System error"),
    CUSTOMER_VALIDATION_ERROR(BAD_REQUEST, "Validation error"),
    ENTITY_NOT_FOUND(NOT_FOUND, "Entity not found"),
    CUSTOMER_INTERNAL_ERROR(INTERNAL_SERVER_ERROR,
            "Operation failed because of an internal network error"),
    CONTACTS_CONFLICT(CONFLICT, "Contact type mismatch for a given account type"),
    CUSTOMER_DOWNSTREAM_SERVICE_TIMEOUT_ERROR(SERVICE_UNAVAILABLE,
            "Downstream profile Service timed out attempting operation"),
    ADDRESS_ALREADY_EXISTS(BAD_REQUEST,"That address already exists"),
    ADDRESS_NOT_FOUND(INTERNAL_SERVER_ERROR,"Address not found"),
    CUSTOMER_MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, "Media type not supported");
    private final HttpStatus httpStatus;
    private final String message;
}
