package com.blinder.api.Movie.mapper;

import com.blinder.api.Movie.dto.MovieResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class MovieCustomMapper {
    public List<MovieResponseDto> movieDataToMovieResponseDto(Mono<String> movieData) throws JsonProcessingException {
        return null;
    }
}
