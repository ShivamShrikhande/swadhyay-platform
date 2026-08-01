package com.swadhyay.kendra.service.impl;

import com.swadhyay.common.exception.ResourceNotFoundException;
import com.swadhyay.kendra.dto.KendraRequest;
import com.swadhyay.kendra.dto.KendraResponse;
import com.swadhyay.kendra.entity.Kendra;
import com.swadhyay.kendra.mapper.KendraMapper;
import com.swadhyay.kendra.repository.KendraRepository;
import com.swadhyay.kendra.service.KendraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KendraServiceImpl implements KendraService {

    private final KendraRepository repository;
    private final KendraMapper mapper;

    @Override
    public KendraResponse createKendra(KendraRequest request) {

        if (repository.existsByName(request.getName())) {
            throw new RuntimeException("Kendra already exists.");
        }

        Kendra kendra = mapper.toEntity(request);

        return mapper.toResponse(
                repository.save(kendra)
        );
    }

    @Override
    public KendraResponse getKendra(Long id) {

        Kendra kendra = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kendra not found"));

        return mapper.toResponse(kendra);
    }

    @Override
    public List<KendraResponse> getAllKendras() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void deleteKendra(Long id) {

        Kendra kendra = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Kendra not found"));

        kendra.setActive(false);

        repository.save(kendra);
    }
}