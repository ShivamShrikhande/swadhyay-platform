package com.swadhyay.auth.controller;

import com.swadhyay.auth.dto.LoginRequest;
import com.swadhyay.auth.dto.LoginResponse;
import com.swadhyay.auth.service.AuthenticationService;
import com.swadhyay.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

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
}