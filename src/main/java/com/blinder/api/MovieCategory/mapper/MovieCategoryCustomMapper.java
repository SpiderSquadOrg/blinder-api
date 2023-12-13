package com.blinder.api.MovieCategory.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MovieCategoryCustomMapper {
    public List<String> movieCategoryDataToList(Mono<String> movieData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = movieData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode results = jsonNode.get("results");

        List<String> movieCategoryList = new ArrayList<>();
        results.forEach((item)->{
            if (!Objects.equals(item.asText(), "null")) movieCategoryList.add(item.asText());
        });

        return movieCategoryList;
    }
}