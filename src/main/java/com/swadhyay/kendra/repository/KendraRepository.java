package com.swadhyay.kendra.repository;

import com.swadhyay.kendra.entity.Kendra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KendraRepository
        extends JpaRepository<Kendra, Long> {

    Optional<Kendra> findByName(String name);

    List<Kendra> findByCity(String city);

    boolean existsByName(String name);

}