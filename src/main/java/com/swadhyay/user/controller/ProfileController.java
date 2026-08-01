package com.swadhyay.user.controller;

import com.swadhyay.common.dto.ApiResponse;
import com.swadhyay.security.user.CustomUserDetails;
import com.swadhyay.user.dto.UpdateProfileRequest;
import com.swadhyay.user.dto.UserResponse;
import com.swadhyay.user.mapper.UserMapper;
import com.swadhyay.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserMapper userMapper;

    private final UserService userService;

    @GetMapping
    public ApiResponse<UserResponse> getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponse response =
                userMapper.toResponse(userDetails.getUser());

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(response)
                .build();
    }

    @PutMapping
    public ApiResponse<UserResponse> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        UserResponse response =
                userService.updateProfile(request);

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Profile updated successfully")
                .data(response)
                .build();
    }

}