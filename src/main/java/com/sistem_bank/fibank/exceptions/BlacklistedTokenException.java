package com.sistem_bank.fibank.exceptions;

public class TokenBlacklistedException extends RuntimeException {
    public TokenBlacklistedException(String message) {
        super(message);
    }
}
