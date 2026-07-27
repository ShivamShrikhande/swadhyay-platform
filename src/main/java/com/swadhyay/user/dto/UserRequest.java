package com.swadhyay.user.dto;

import com.swadhyay.user.entity.Gender;
import com.swadhyay.user.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private Role role;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String state;

    private String district;

    private String city;

    private String village;
}