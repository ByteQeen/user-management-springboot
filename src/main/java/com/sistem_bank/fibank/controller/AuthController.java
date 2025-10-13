package com.sistem_bank.fibank.controller;
import com.sistem_bank.fibank.dto.*;
import com.sistem_bank.fibank.security.AccessTokenService;
import com.sistem_bank.fibank.security.RefreshTokenService;
import com.sistem_bank.fibank.service.AuthService;
import com.sistem_bank.fibank.service.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500")
public class AuthController {
    private final AuthService authService;
    private final TokenBlacklistService blacklistService;
    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest request) {
     return new ResponseEntity<>(authService.refresh(request), HttpStatus.OK);
    }


   // @CrossOrigin(origins = "http://127.0.0.1:5500", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
    @PostMapping("/api/auth/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) {
        return new ResponseEntity<> (authService.signUp (signupRequest), HttpStatus.CREATED);
    }


    @PostMapping("/api/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(authService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authHeader,
                                    @RequestParam("refreshToken") String refreshToken) {
        try {
            log.info("Logout request received");

            // Extract accessToken from header
            String accessToken = authHeader.substring(7);
            log.info("Access token extracted (first 20 chars): {}", accessToken.substring(0, Math.min(20, accessToken.length())));

            // Calculate remaining time until token expires
            Long expirationTime = accessTokenService.extractExpirationFromToken(accessToken);
            long currentTime = System.currentTimeMillis();
            long remainingSeconds = (expirationTime - currentTime) / 1000;

            log.info("Token expiration time: {}, Current time: {}, Remaining seconds: {}",
                    expirationTime, currentTime, remainingSeconds);

            // Only blacklist if token hasn't expired yet
            if (remainingSeconds > 0) {
                log.info("Blacklisting token with {} seconds remaining", remainingSeconds);
                blacklistService.blacklistToken(accessToken, remainingSeconds);
            } else {
                log.warn("Token already expired. Not blacklisting. Remaining seconds: {}", remainingSeconds);
            }

            // Revoke refresh token
            String jti = refreshTokenService.extractJtiFromToken(refreshToken);
            refreshTokenService.revokeToken(jti);

            log.info("Logout successful");
            return ResponseEntity.ok("Logout successfully");

        } catch (Exception e) {
            log.error("Error during logout", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Logout failed");
        }
    }


    @GetMapping("/secure")
    public ResponseEntity<String> sayHello() {
        return new ResponseEntity<>("Hello", HttpStatus.OK);
    }


    @GetMapping("/api/user/profile")
    public ResponseEntity<String> profile() {
        return new ResponseEntity<>("User profile", HttpStatus.OK);
    }


    @GetMapping("/api/admin/users")
    public ResponseEntity<String> getUsers() {
        return new ResponseEntity<>("Users", HttpStatus.OK);
    }
}
