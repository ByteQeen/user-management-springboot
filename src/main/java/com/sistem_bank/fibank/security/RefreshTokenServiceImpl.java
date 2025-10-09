package com.sistem_bank.fibank.security;

import com.sistem_bank.fibank.domain.RefreshToken;
import com.sistem_bank.fibank.domain.User;
import com.sistem_bank.fibank.exceptions.RefreshTokenNotFoundException;
import com.sistem_bank.fibank.exceptions.UserNotFoundException;
import com.sistem_bank.fibank.repository.RefreshTokenRepository;
import com.sistem_bank.fibank.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService{
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.refresh.expirationMs}")
    private Long tokenExpirationMs;

    private SecretKey secretKey;


    @PostConstruct
    public void init(){
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public RefreshToken generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "REFRESH");
        String jti = UUID.randomUUID().toString();
        String token= Jwts
                .builder()
                .claims()
                .subject(userDetails.getUsername())
                .id(jti)
                .expiration(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpirationMs))
                .and()
                .signWith(secretKey)
                .compact();

        //find user to set the user id in the JWT Refresh Token
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return RefreshToken.builder()
                .token(token)
                .jti(jti)
                .user(user)
                .expirationDate(LocalDateTime.now().plusSeconds(tokenExpirationMs/1000))
                .revoked(false)
                .build();
    }

    @Override
    public RefreshToken findByJti(String jti) {
        return refreshTokenRepository.findByJti(jti)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found"));
    }


    @Override
    public boolean isExpired(String jti) {
        RefreshToken refreshToken = refreshTokenRepository.findByJti(jti)
                .orElseThrow(() -> new RefreshTokenNotFoundException("Refresh token not found."));

        Claims claims =extractAllClaims(refreshToken.getToken());
        return claims.getExpiration().before(new Date());
    }

    @Override
    public boolean revokeToken(String jti) {
        RefreshToken refreshToken = refreshTokenRepository.findByJti(jti)
                .orElseThrow(()-> new RefreshTokenNotFoundException("Refresh token not found."));

        refreshToken.setRevoked(true);
        RefreshToken saved = refreshTokenRepository.save(refreshToken);
        return saved.isRevoked();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        String email = claims.getSubject();
        String jti = claims.getId();

        return (email.equals(userDetails.getUsername()) && !isExpired(token) && !revokeToken(jti));
    }

    @Override
    public String extractJtiFromToken(String token){
        Claims claims = extractAllClaims(token);
        return claims.getId();
    }

    @Override
    public void saveRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}

