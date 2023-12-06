package com.blinder.api.Movie.controller;

import com.blinder.api.Movie.dto.MovieResponseDto;
import com.blinder.api.Movie.mapper.MovieCustomMapper;
import com.blinder.api.Movie.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/movies")
@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final MovieCustomMapper movieCustomMapper;

    @GetMapping("/search")
    public ResponseEntity<List<MovieResponseDto>> searchMovie(@RequestParam(name = "movieName") String movieName, @RequestParam(name = "limit", defaultValue = "50") int limit) throws JsonProcessingException {
        return new ResponseEntity<>(movieCustomMapper.movieDataToMovieResponseDto(movieService.searchMovie(movieName, limit)), HttpStatus.OK);
    }
}
