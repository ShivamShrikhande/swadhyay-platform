package com.swadhyay.auth.service;

import com.swadhyay.auth.dto.LoginRequest;
import com.swadhyay.auth.dto.LoginResponse;
import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.security.jwt.JwtService;
import com.swadhyay.security.user.CustomUserDetails;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}