package com.swadhyay.kendra.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KendraResponse {

    private Long id;

    private String name;

    private String country;

    private String state;

    private String district;

    private String city;

    private Double latitude;

    private Double longitude;

    private String description;

    private Boolean active;

}