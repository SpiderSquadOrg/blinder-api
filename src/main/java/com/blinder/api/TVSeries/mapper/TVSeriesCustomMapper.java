package com.blinder.api.TVSeries.mapper;

import com.blinder.api.Movie.dto.MovieResponseDto;
import com.blinder.api.TVSeries.dto.TVSeriesDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class TVSeriesCustomMapper {
    public List<TVSeriesDto> tvSeriesDataToTvSeriesResponseDto(Mono<String> tvSeriesData) throws JsonProcessingException {
        List<TVSeriesDto> tvSeriesDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = tvSeriesData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode.get("results");

        items.forEach((item) -> {
            TVSeriesDto tvSeriesDto = new TVSeriesDto();
            tvSeriesDto.setImdbId(item.get("_id").asText());
            tvSeriesDto.setName(item.get("titleText").get("text").asText());
            try{
                tvSeriesDto.setYear(item.get("releaseYear").get("year").asInt());
                tvSeriesDto.setImage(item.get("primaryImage").get("url").asText());
                tvSeriesDtos.add(tvSeriesDto);
            }catch (NullPointerException ignored){

            }
        });

        return tvSeriesDtos;

    }
}
