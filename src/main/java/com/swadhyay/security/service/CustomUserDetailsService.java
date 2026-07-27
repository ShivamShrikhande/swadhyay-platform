package com.swadhyay.security.service;

import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.security.user.CustomUserDetails;
import com.swadhyay.user.entity.User;
import com.swadhyay.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobileNumber) {

        User user = userRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with mobile number: " + mobileNumber));

        return new CustomUserDetails(user);
    }
}