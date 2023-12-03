package com.blinder.api.filter.repository;

import com.blinder.api.filter.model.Filter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FilterRepository extends JpaRepository<Filter, String> {
    Optional<Filter> findByUserId(String userId);
}
