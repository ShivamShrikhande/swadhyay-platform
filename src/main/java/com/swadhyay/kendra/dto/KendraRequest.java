package com.swadhyay.kendra.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class KendraRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String district;

    @NotBlank
    private String city;

    private Double latitude;

    private Double longitude;

    private String description;

}