package com.swadhyay.user.dto;

import com.swadhyay.user.entity.Gender;
import com.swadhyay.user.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String email;

    private Role role;

    private Gender gender;

    private LocalDate dateOfBirth;

//    private String state;
//
//    private String district;
//
//    private String city;
//
//    private String village;

    private Long kendraId;

    private String kendraName;

    private Boolean active;
}