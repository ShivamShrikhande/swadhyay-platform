package com.swadhyay.user.service;

import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    void deleteUser(Long id);
}