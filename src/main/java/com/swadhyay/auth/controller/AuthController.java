package com.swadhyay.auth.controller;

import com.swadhyay.auth.dto.*;
import com.swadhyay.auth.service.AuthenticationService;
import com.swadhyay.common.dto.ApiResponse;
import com.swadhyay.user.dto.ChangePasswordRequest;
import com.swadhyay.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.login(request);

        return ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login Successful")
                .data(response)
                .build();
    }
    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        userService.changePassword(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password changed successfully")
                .data(null)
                .build();
    }
    // ===========================
    // FORGOT PASSWORD
    // ===========================
    @PostMapping("/forgot-password")
    public ApiResponse<ForgotPasswordResponse> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        ForgotPasswordResponse response =
                authenticationService.forgotPassword(request);

        return ApiResponse.<ForgotPasswordResponse>builder()
                .success(true)
                .message("OTP generated successfully")
                .data(response)
                .build();
    }

    // ===========================
    // RESET PASSWORD
    // ===========================
    @PostMapping("/reset-password")
    public ApiResponse<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        authenticationService.resetPassword(request);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password reset successfully")
                .data(null)
                .build();
    }

}
