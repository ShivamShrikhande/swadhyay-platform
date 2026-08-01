package com.swadhyay.kendra.mapper;

import com.swadhyay.kendra.dto.KendraRequest;
import com.swadhyay.kendra.dto.KendraResponse;
import com.swadhyay.kendra.entity.Kendra;
import org.springframework.stereotype.Component;

@Component
public class KendraMapper {

    public Kendra toEntity(KendraRequest request) {

        return Kendra.builder()
                .name(request.getName())
                .country(request.getCountry())
                .state(request.getState())
                .district(request.getDistrict())
                .city(request.getCity())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .description(request.getDescription())
                .active(true)
                .build();
    }

    public KendraResponse toResponse(Kendra kendra) {

        return KendraResponse.builder()
                .id(kendra.getId())
                .name(kendra.getName())
                .country(kendra.getCountry())
                .state(kendra.getState())
                .district(kendra.getDistrict())
                .city(kendra.getCity())
                .latitude(kendra.getLatitude())
                .longitude(kendra.getLongitude())
                .description(kendra.getDescription())
                .active(kendra.getActive())
                .build();
    }
}