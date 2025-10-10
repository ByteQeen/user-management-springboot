package com.sistem_bank.fibank.controller;
import com.sistem_bank.fibank.domain.RefreshToken;
import com.sistem_bank.fibank.domain.UserPrincipal;
import com.sistem_bank.fibank.dto.*;
import com.sistem_bank.fibank.security.AccessTokenService;
import com.sistem_bank.fibank.security.RefreshTokenService;
import com.sistem_bank.fibank.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5500")
public class AuthController {
    private final AuthService authService;

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
