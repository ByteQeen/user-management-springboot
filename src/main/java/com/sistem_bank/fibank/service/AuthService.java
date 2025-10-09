package com.sistem_bank.fibank.service;

import com.sistem_bank.fibank.dto.*;
import java.util.List;

public interface AuthService {
    SignupResponse signUp(SignupRequest signupRequest);
    LoginResponse login(LoginRequest loginRequest);
}

