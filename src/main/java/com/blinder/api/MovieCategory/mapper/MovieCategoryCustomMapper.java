package com.blinder.api.MovieCategory.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MovieCategoryCustomMapper {
    public JsonNode movieCategoryDataToList(Mono<String> movieData) throws JsonProcessingException {


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = movieData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        return jsonNode.get("results");


    }
}