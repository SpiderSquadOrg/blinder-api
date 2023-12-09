package com.blinder.api.location.mapper;

import com.blinder.api.Movie.dto.MovieResponseDto;
import com.blinder.api.location.dto.LocationDto;
import com.blinder.api.location.model.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationCustomMapper {
    public List<LocationDto> locationCountryDataToLocationResponseDto(Mono<String> countryData) throws JsonProcessingException {
        List<LocationDto> locationDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = countryData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode;

        items.forEach((item) -> {
            LocationDto locationDto = new LocationDto();
            locationDto.setId(String.valueOf(item.get("id").asInt()));
            locationDto.setName(item.get("name").asText());
            locationDto.setIso2(item.get("iso2").asText());
            locationDtos.add(locationDto);
        });

        return locationDtos;

    }


}
