package com.swadhyay.user.service.impl;

import com.swadhyay.auth.dto.ForgotPasswordRequest;
import com.swadhyay.auth.dto.ForgotPasswordResponse;
import com.swadhyay.common.exception.InvalidRequestException;
import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.common.exception.UserAlreadyExistsException;
import com.swadhyay.user.dto.ChangePasswordRequest;
import com.swadhyay.user.dto.UpdateProfileRequest;
import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;
import com.swadhyay.user.entity.Role;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.mapper.UserMapper;
import com.swadhyay.user.repository.UserRepository;
import com.swadhyay.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + id));

        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id : " + id));

        user.setActive(false);

        userRepository.save(user);
    }
    @Override
    public UserResponse getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }
    @Override
    public UserResponse updateProfile(UpdateProfileRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Update editable fields
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setState(request.getState());
        user.setDistrict(request.getDistrict());
        user.setCity(request.getCity());
        user.setVillage(request.getVillage());

        User updatedUser = userRepository.save(user);

        return userMapper.toResponse(updatedUser);
    }
    @Override
    public void changePassword(ChangePasswordRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String mobileNumber = authentication.getName();

        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Verify current password
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidRequestException("Current password is incorrect.");
        }

        // Verify new password and confirm password
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new InvalidRequestException("New password and confirm password do not match.");
        }

        // Optional: Prevent using the same password again
        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new InvalidRequestException("New password cannot be the same as the current password.");
        }

        // Update password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }


}