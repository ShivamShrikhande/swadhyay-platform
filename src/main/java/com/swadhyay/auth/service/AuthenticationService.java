package com.swadhyay.auth.service;

import com.swadhyay.auth.dto.*;
import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.security.jwt.JwtService;
import com.swadhyay.security.user.CustomUserDetails;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid mobile number or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResourceNotFoundException("Invalid mobile number or password");
        }

        String token = jwtService.generateToken(new CustomUserDetails(user));

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .userId(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(user.getRole().name())
                .build();
    }
    // ===========================
    // FORGOT PASSWORD
    // ===========================
    public ForgotPasswordResponse forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        String otp = generateOtp();

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        userRepository.save(user);

        return ForgotPasswordResponse.builder()
                .otp(otp)
                .build();
    }

    // ===========================
    // RESET PASSWORD
    // ===========================
    public void resetPassword(ResetPasswordRequest request) {

        User user = userRepository.findByMobileNumber(request.getMobileNumber())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (user.getOtp() == null) {
            throw new RuntimeException("OTP not generated.");
        }

        if (!user.getOtp().equals(request.getOtp())) {
            throw new RuntimeException("Invalid OTP.");
        }

        if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired.");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        user.setOtp(null);
        user.setOtpExpiry(null);

        userRepository.save(user);
    }

    // ===========================
    // GENERATE OTP
    // ===========================
    private String generateOtp() {

        Random random = new Random();

        int otp = 100000 + random.nextInt(900000);

        return String.valueOf(otp);
    }
}