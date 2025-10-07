package com.sistem_bank.fibank.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotNull(message = "Full name cannot be null")
    @NotEmpty(message = "Full name is required")
    @Size(min = 3, max = 100, message = "Full name must be between 3 and 100 characters")
    private String fullName;


    @NotNull(message = "Email cannot be null")
    @Email(message = "Invalid email address")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "Confirm password cannot be null")
    @Size(min = 8, message = "Confirm password must be at least 8 characters")
    private String confirmPassword;
    private boolean termsAccepted;
    private boolean privacyAccepted;
}

