package com.swadhyay.user.controller;

import com.swadhyay.common.dto.ApiResponse;
import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;
import com.swadhyay.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @PostMapping("/register")
//    public UserResponse register(@Valid @RequestBody UserRequest request) {
//        return userService.registerUser(request);
//    }
@PostMapping("/register")
public ApiResponse<UserResponse> register(@Valid @RequestBody UserRequest request){

    UserResponse response = userService.registerUser(request);

    return ApiResponse.<UserResponse>builder()
            .success(true)
            .message("User registered successfully")
            .data(response)
            .build();
}
}