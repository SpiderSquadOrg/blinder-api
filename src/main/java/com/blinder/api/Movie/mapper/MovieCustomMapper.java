package com.blinder.api.Movie.mapper;

import com.blinder.api.Movie.dto.MovieResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class MovieCustomMapper {
    public List<MovieResponseDto> movieDataToMovieResponseDto(Mono<String> movieData) throws JsonProcessingException {
        List<MovieResponseDto> movieResponseDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = movieData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode.get("results");

        items.forEach((item) -> {
            MovieResponseDto movieResponseDto = new MovieResponseDto();
            movieResponseDto.setImdbId(item.get("_id").asText());
            movieResponseDto.setName(item.get("titleText").get("text").asText());
            try{
                movieResponseDto.setYear(item.get("releaseYear").get("year").asInt());
                movieResponseDto.setImage(item.get("primaryImage").get("url").asText());
                movieResponseDtos.add(movieResponseDto);
            }catch (NullPointerException ignored){

            }
        });

        return movieResponseDtos;

    }
}
