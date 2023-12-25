package dev.concat.vab.ecomhotelappbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidLoginResponseException extends RuntimeException {

    public InvalidLoginResponseException(String message) {
        super(message);
    }
}