package com.sistem_bank.fibank.dto;
import lombok.Data;

@Data
public class SignupResponse {
    private Long id;
    private String username;
    private String email;
    private String message;
    private String token;
    private String refreshToken;
}
