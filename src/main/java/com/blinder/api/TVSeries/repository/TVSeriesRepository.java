package com.blinder.api.TVSeries.repository;

import com.blinder.api.TVSeries.model.TVSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TVSeriesRepository extends JpaRepository<TVSeries, String> {
}