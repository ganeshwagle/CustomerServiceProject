package com.customer_service.customer_service.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Marker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponse> handleSystemException(SystemException systemException){
        Error error = new Error(systemException.getErrorCode(),"",systemException.getMessage());
        List<Error> errors= new ArrayList<>();
        errors.add(error);
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, systemException.getErrorCode().getHttpStatus());
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

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleExceptions(Exception exception) {
        System.out.println("in here");
        List<Error> errors = new ArrayList<>();
        errors.add(new Error(ErrorCode.CUSTOMER_VALIDATION_ERROR, "", exception.getMessage()
                ));
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
