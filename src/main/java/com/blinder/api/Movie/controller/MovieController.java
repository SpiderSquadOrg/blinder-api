package com.blinder.api.Movie.controller;

import com.blinder.api.Movie.service.impl.MovieServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/search")
public class MovieController {

    private final MovieServiceImpl movieServiceImpl;

    @Autowired
    public MovieController(MovieServiceImpl movieServiceImpl) {
        this.movieServiceImpl = movieServiceImpl;
    }

    @GetMapping("/{movieName}")
    public Mono<ResponseEntity<String>> searchMovie(@PathVariable String movieName) throws JsonProcessingException {
        return movieServiceImpl.searchMovie(movieName)
                .map(movie -> ResponseEntity.ok().body(movie))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
