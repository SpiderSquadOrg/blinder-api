package com.blinder.api.MovieCategory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface MovieCategoryService {
    Mono<String> getMovieCategories() throws JsonProcessingException;

}