package com.sistem_bank.fibank.service;

import com.sistem_bank.fibank.domain.RefreshToken;
import com.sistem_bank.fibank.domain.Role;
import com.sistem_bank.fibank.domain.User;
import com.sistem_bank.fibank.domain.UserPrincipal;
import com.sistem_bank.fibank.dto.*;
import com.sistem_bank.fibank.exceptions.InvalidRefreshTokenException;
import com.sistem_bank.fibank.exceptions.PasswordMismatchException;
import com.sistem_bank.fibank.exceptions.RoleNotFoundException;
import com.sistem_bank.fibank.exceptions.UserAlreadyExistsException;
import com.sistem_bank.fibank.mapper.UserMapper;
import com.sistem_bank.fibank.repository.RefreshTokenRepository;
import com.sistem_bank.fibank.repository.RoleRepository;
import com.sistem_bank.fibank.repository.UserRepository;
import com.sistem_bank.fibank.security.AccessTokenService;
import com.sistem_bank.fibank.security.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AccessTokenService accessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public SignupResponse signUp(SignupRequest signupRequest) {
        if(userRepository.existsByUsername (signupRequest.getUsername ())) {
            log.info("Username already exists: {}", signupRequest.getUsername());
            throw new UserAlreadyExistsException("Username already taken!");
        }

        if(!signupRequest.getConfirmPassword ().equals (signupRequest.getPassword ())){
            log.info("Password don't match.");
            throw new PasswordMismatchException("Passwords do not match!");
        }

        User user = new User ();
        user.setUsername (signupRequest.getUsername ());
        user.setEmail (signupRequest.getEmail ());
        user.setPhoneNumber(signupRequest.getPhoneNumber());
        user.setPassword (passwordEncoder.encode (signupRequest.getPassword ()));

        Role defaultRole = roleRepository.findByName ("USER")
                .orElseThrow (() -> new RoleNotFoundException("Role not found!"));

        user.setRoles (Set.of (defaultRole));
        User savedUser = userRepository.save (user);

        log.info("User registered successfully");

        UserPrincipal userPrincipal = new UserPrincipal(savedUser);
        String accessToken = accessTokenService.generateToken(userPrincipal);

        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userPrincipal);
        refreshTokenService.saveRefreshToken(refreshToken);
        SignupResponse signupResponse = userMapper.toSignupResponse(savedUser);
        signupResponse.setAccessToken(accessToken);
        signupResponse.setRefreshToken(refreshToken.getToken());
        return signupResponse;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = accessTokenService.generateToken(userPrincipal);
        log.info("Access token issued successfully:)");
        RefreshToken refreshToken = refreshTokenService.generateRefreshToken(userPrincipal);
        refreshTokenService.saveRefreshToken(refreshToken);
        log.info("Refresh token issued successfully!");
        log.info("User logged successfully");

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setRefreshToken(refreshToken.getToken());
        loginResponse.setTokenType("Bearer");
        loginResponse.setUserId(userPrincipal.getId());

        return loginResponse;
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        String refreshTokenFromRequest = refreshTokenRequest.getRefreshToken();
        String jti = refreshTokenService.extractJtiFromToken(refreshTokenFromRequest);
        RefreshToken refreshToken = refreshTokenService.findByJti(jti);

        UserPrincipal userPrincipal = new UserPrincipal(refreshToken.getUser());
        if(!refreshTokenService.isTokenValid(refreshTokenFromRequest, userPrincipal)) {
            log.warn("Refresh token with jti {} is invalid", refreshToken.getJti());
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        String newAccessToken = accessTokenService.generateToken(userPrincipal);
        log.info("Issued new access token for user {}", refreshToken.getUser().getUsername());

        RefreshToken newRefreshToken = refreshTokenService.generateRefreshToken(userPrincipal);

        log.info("Issued new refresh token for user {}", refreshToken.getUser().getUsername());
        refreshTokenRepository.save(newRefreshToken);
        log.info("New refresh token for user {} was saved", newRefreshToken.getUser().getUsername());
        refreshTokenService.revokeToken(jti);
        log.info("Old refresh token for user {} was revoked", refreshToken.getUser().getUsername());

        return new RefreshTokenResponse(newAccessToken, newRefreshToken.getToken());
    }
}

