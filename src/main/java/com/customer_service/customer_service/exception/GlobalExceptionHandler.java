package com.customer_service.customer_service.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebInputException;


import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(SystemException systemException) {
        Error error = new Error(systemException.getErrorCode(), "", systemException.getMessage());
        List<Error> errors = new ArrayList<>();
        errors.add(error);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public final ResponseEntity<ErrorResponse> handleValidationExceptions(WebExchangeBindException exception) {
        List<Error> errors = new ArrayList<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.add(new Error(ErrorCode.CUSTOMER_VALIDATION_ERROR,
                        error.getField(), error.getDefaultMessage())));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public final ResponseEntity<ErrorResponse> handleMongoException(DuplicateKeyException exception){
        List<Error> errors = new ArrayList<>();
        errors.add(new Error(ErrorCode.CUSTOMER_VALIDATION_ERROR, "email","That email is already taken"
        ));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public final ResponseEntity<ErrorResponse> handleServerWebInputExceptions(ServerWebInputException exception) {
        List<Error> errors = new ArrayList<>();
        errors.add(new Error(ErrorCode.INVALID_INPUT, "", "Invalid input!!!"
        ));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleExceptions(Exception exception) {
        List<Error> errors = new ArrayList<>();
        errors.add(new Error(ErrorCode.CUSTOMER_VALIDATION_ERROR, "", exception.getClass().getName()
        ));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

