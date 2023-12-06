package com.blinder.api.Movie.repository;

import com.blinder.api.Movie.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, String> {
}