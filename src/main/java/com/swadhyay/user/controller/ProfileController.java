package com.swadhyay.user.controller;

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
    public UserResponse getProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        return userMapper.toResponse(userDetails.getUser());
    }

    @PutMapping
    public UserResponse updateProfile(
            @Valid @RequestBody UpdateProfileRequest request) {

        return userService.updateProfile(request);
    }

}