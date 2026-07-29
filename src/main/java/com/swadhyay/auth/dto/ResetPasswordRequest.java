package com.swadhyay.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String otp;

    @NotBlank
    private String newPassword;

    @NotBlank
    private String confirmPassword;
}