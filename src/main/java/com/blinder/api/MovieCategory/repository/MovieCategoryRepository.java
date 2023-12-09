package com.blinder.api.MovieCategory.repository;

import com.blinder.api.MovieCategory.model.MovieCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieCategoryRepository extends JpaRepository<MovieCategory, String> {

    boolean existsByName(String name);
}
