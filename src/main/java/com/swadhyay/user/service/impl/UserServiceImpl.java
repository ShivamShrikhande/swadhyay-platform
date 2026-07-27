package com.swadhyay.user.service.impl;

import com.swadhyay.common.exception.UserAlreadyExistsException;
import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;
import com.swadhyay.user.entity.Role;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.mapper.UserMapper;
import com.swadhyay.user.repository.UserRepository;
import com.swadhyay.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse registerUser(UserRequest request) {

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
//            throw new RuntimeException("Mobile number already registered.");
            throw new UserAlreadyExistsException("Mobile number already registered.");
        }

        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                userRepository.existsByEmail(request.getEmail())) {

            //throw new RuntimeException("Email already registered.");
            throw new UserAlreadyExistsException("Email already registered.");
        }

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.MEMBER);
        }

        user.setActive(true);

        User savedUser = userRepository.save(user);

        return userMapper.toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}