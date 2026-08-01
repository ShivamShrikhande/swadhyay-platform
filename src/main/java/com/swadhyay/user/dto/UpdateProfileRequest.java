package com.swadhyay.user.dto;

import com.swadhyay.user.entity.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateProfileRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Invalid email")
    private String email;

    private Gender gender;

    private LocalDate dateOfBirth;

    @NotNull(message = "Kendra is required")
    private Long kendraId;

}