package com.sistem_bank.fibank.service;

import com.sistem_bank.fibank.dto.*;

public interface AuthService {
    SignupResponse signUp(SignupRequest signupRequest);
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
}

