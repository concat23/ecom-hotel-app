package dev.concat.vab.ecomhotelappbackend.exception;

public class InvalidEcomBookingRequestException extends RuntimeException{
    public InvalidEcomBookingRequestException(String message){
        super(message);
    }
}

