package dev.concat.vab.ecomhotelappbackend.exception;

public class InternalServerException extends RuntimeException{
    public InternalServerException(String message){
        super(message);
    }
}
