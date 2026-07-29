package com.swadhyay.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ForgotPasswordResponse {

    private String otp;
}