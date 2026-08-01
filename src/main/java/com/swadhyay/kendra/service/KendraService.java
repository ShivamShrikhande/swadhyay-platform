package com.swadhyay.kendra.service;

import com.swadhyay.kendra.dto.KendraRequest;
import com.swadhyay.kendra.dto.KendraResponse;

import java.util.List;

public interface KendraService {

    KendraResponse createKendra(KendraRequest request);

    KendraResponse getKendra(Long id);

    List<KendraResponse> getAllKendras();

    void deleteKendra(Long id);

}