package com.swadhyay.user.mapper;

import com.swadhyay.user.dto.UserRequest;
import com.swadhyay.user.dto.UserResponse;
import com.swadhyay.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .mobileNumber(request.getMobileNumber())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .state(request.getState())
                .district(request.getDistrict())
                .city(request.getCity())
                .village(request.getVillage())
                .active(true)
                .build();
    }

    public UserResponse toResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .mobileNumber(user.getMobileNumber())
                .email(user.getEmail())
                .role(user.getRole())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .state(user.getState())
                .district(user.getDistrict())
                .city(user.getCity())
                .village(user.getVillage())
                .active(user.getActive())
                .build();
    }
}