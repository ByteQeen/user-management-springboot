package com.sistem_bank.fibank.security;


import com.sistem_bank.fibank.domain.RefreshToken;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface RefreshTokenService {
    RefreshToken generateRefreshToken(UserDetails userDetails);
    RefreshToken findByJti(String jti);
    boolean isExpired(String jti);
    boolean revokeToken(String jti);
    Claims extractAllClaims(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    String extractJtiFromToken(String token);
    void saveRefreshToken(RefreshToken refreshToken);
}
