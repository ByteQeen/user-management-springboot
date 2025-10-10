package com.sistem_bank.fibank.exceptions;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message){
        super(message);
    }
}
