package com.blinder.api.Movie.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface MovieService {
    Mono<String> searchMovie(String movieName) throws JsonProcessingException;
}
