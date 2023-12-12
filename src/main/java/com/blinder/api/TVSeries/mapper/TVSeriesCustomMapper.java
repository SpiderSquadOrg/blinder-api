package com.blinder.api.TVSeries.mapper;

import com.blinder.api.TVSeries.dto.TVSeriesResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class TVSeriesCustomMapper {
    public List<TVSeriesResponseDto> tvSeriesDataToTvSeriesResponseDto(Mono<String> tvSeriesData) throws JsonProcessingException {
        List<TVSeriesResponseDto> tvSeriesResponseDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = tvSeriesData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode.get("results");

        items.forEach((item) -> {
            TVSeriesResponseDto tvSeriesResponseDto = new TVSeriesResponseDto();
            tvSeriesResponseDto.setImdbId(item.get("_id").asText());
            tvSeriesResponseDto.setName(item.get("titleText").get("text").asText());
            try{
                tvSeriesResponseDto.setYear(item.get("releaseYear").get("year").asInt());
                tvSeriesResponseDto.setImage(item.get("primaryImage").get("url").asText());
                tvSeriesResponseDtos.add(tvSeriesResponseDto);
            }catch (NullPointerException ignored){

            }
        });

        return tvSeriesResponseDtos;

    }
}
