package com.customer_service.customer_service.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    List<Error> errors = new ArrayList<>();
}
