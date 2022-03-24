package com.customer_service.customer_service.exception;

import lombok.Getter;

@Getter
public class SystemException extends Exception{
    private final ErrorCode errorCode;
    public SystemException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public SystemException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}
