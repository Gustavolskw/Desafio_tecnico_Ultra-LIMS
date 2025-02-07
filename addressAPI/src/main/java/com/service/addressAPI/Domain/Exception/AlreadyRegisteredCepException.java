package com.service.addressAPI.Domain.Exception;

public class AlreadyRegisteredCepException extends RuntimeException {
    public AlreadyRegisteredCepException(String message) {
        super(message);
    }
}
