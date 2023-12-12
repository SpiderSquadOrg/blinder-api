package com.blinder.api.MusicCategory.mapper;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MusicCategoryCustomMapper {
    public JsonNode musicCategoryDataToList(Mono<String> musicData) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = musicData.block();
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        return jsonNode.get("genres");
    }
}