package com.sistem_bank.fibank.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccessTokenService {
    String generateToken(UserDetails userDetails);

    String extractEmailFromToken(String token);

    boolean validateToken(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Claims extractAllClaims(String token);
}
