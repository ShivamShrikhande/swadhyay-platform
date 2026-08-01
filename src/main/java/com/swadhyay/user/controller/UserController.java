//package com.swadhyay.user.controller;
//
//import com.swadhyay.common.dto.ApiResponse;
//import com.swadhyay.user.dto.UserRequest;
//import com.swadhyay.user.dto.UserResponse;
//import com.swadhyay.user.service.UserService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService userService;
//
////    @PostMapping("/register")
////    public UserResponse register(@Valid @RequestBody UserRequest request) {
////        return userService.registerUser(request);
////    }
//@PostMapping("/register")
//public ApiResponse<UserResponse> register(@Valid @RequestBody UserRequest request){
//
//    UserResponse response = userService.registerUser(request);
//
//    return ApiResponse.<UserResponse>builder()
//            .success(true)
//            .message("User registered successfully")
//            .data(response)
//            .build();
//}
//    @GetMapping("/me")
//    public ApiResponse<UserResponse> getCurrentUser() {
//
//        UserResponse response = userService.getCurrentUser();
//
//        return ApiResponse.<UserResponse>builder()
//                .success(true)
//                .message("User fetched successfully")
//                .data(response)
//                .build();
//    }
//    @GetMapping("/{id}")
//    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
//
//        UserResponse response = userService.getUserById(id);
//
//        return ApiResponse.<UserResponse>builder()
//                .success(true)
//                .message("User fetched successfully")
//                .data(response)
//                .build();
//    }
//    @GetMapping
//    public ApiResponse<List<UserResponse>> getAllUsers() {
//
//        List<UserResponse> response = userService.getAllUsers();
//
//        return ApiResponse.<List<UserResponse>>builder()
//                .success(true)
//                .message("Users fetched successfully")
//                .data(response)
//                .build();
//    }
//    @DeleteMapping("/{id}")
//    public ApiResponse<Void> deleteUser(@PathVariable Long id) {
//
//        userService.deleteUser(id);
//
//        return ApiResponse.<Void>builder()
//                .success(true)
//                .message("User deleted successfully")
//                .data(null)
//                .build();
//    }
//}

package com.swadhyay.user.controller;

import com.swadhyay.common.dto.ApiResponse;
import com.swadhyay.user.dto.MemberSearchResponse;
import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;
import com.swadhyay.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody UserRequest request) {

        UserResponse response = userService.registerUser(request);

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(response)
                .build();
    }

    @GetMapping("/me")
    public ApiResponse<UserResponse> getCurrentUser() {

        UserResponse response = userService.getCurrentUser();

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User fetched successfully")
                .data(response)
                .build();
    }
    @GetMapping("/search")
    public ApiResponse<List<MemberSearchResponse>> searchMembers(
            @RequestParam String keyword) {
        System.out.println("========== SEARCH API HIT ==========");
        List<MemberSearchResponse> response =
                userService.searchMembers(keyword);

        return ApiResponse.<List<MemberSearchResponse>>builder()
                .success(true)
                .message("Members fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {

        UserResponse response = userService.getUserById(id);

        return ApiResponse.<UserResponse>builder()
                .success(true)
                .message("User fetched successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {

        List<UserResponse> response = userService.getAllUsers();

        return ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(response)
                .build();
    }

    // ===========================
    // SEARCH MEMBERS
    // ===========================
//    @GetMapping("/search")
//    public ApiResponse<List<MemberSearchResponse>> searchMembers(
//            @RequestParam String keyword) {
//
//        List<MemberSearchResponse> response =
//                userService.searchMembers(keyword);
//
//        return ApiResponse.<List<MemberSearchResponse>>builder()
//                .success(true)
//                .message("Members fetched successfully")
//                .data(response)
//                .build();
//    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("User deleted successfully")
                .data(null)
                .build();
    }
}