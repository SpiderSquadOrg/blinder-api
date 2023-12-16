package com.blinder.api.TVSeriesCategories.repository;

import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVSeriesCategoryRepository extends JpaRepository<TVSeriesCategory, String> {
}
