package com.swadhyay.kendra.controller;

import com.swadhyay.common.dto.ApiResponse;
import com.swadhyay.kendra.dto.KendraRequest;
import com.swadhyay.kendra.dto.KendraResponse;
import com.swadhyay.kendra.service.KendraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kendras")
@RequiredArgsConstructor
public class KendraController {

    private final KendraService service;

    @PostMapping
    public ApiResponse<KendraResponse> create(
            @Valid @RequestBody KendraRequest request) {

        KendraResponse response = service.createKendra(request);

        return ApiResponse.<KendraResponse>builder()
                .success(true)
                .message("Kendra created successfully")
                .data(response)
                .build();
    }

    @GetMapping
    public ApiResponse<List<KendraResponse>> getAll() {

        return ApiResponse.<List<KendraResponse>>builder()
                .success(true)
                .message("Kendras fetched successfully")
                .data(service.getAllKendras())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<KendraResponse> getById(@PathVariable Long id) {

        return ApiResponse.<KendraResponse>builder()
                .success(true)
                .message("Kendra fetched successfully")
                .data(service.getKendra(id))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {

        service.deleteKendra(id);

        return ApiResponse.<Void>builder()
                .success(true)
                .message("Kendra deleted successfully")
                .data(null)
                .build();
    }
}