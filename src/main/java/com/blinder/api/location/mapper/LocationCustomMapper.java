package com.blinder.api.location.mapper;
import com.blinder.api.location.dto.LocationCountryDto;
import com.blinder.api.location.dto.LocationStateDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
public class LocationCustomMapper {
    public List<LocationCountryDto> locationCountryDataToLocationResponseDto(Mono<String> countryData) throws JsonProcessingException {
        List<LocationCountryDto> locationCountryDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = countryData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode;

        items.forEach((item) -> {
            LocationCountryDto locationCountryDto = new LocationCountryDto();
            locationCountryDto.setCountryId(String.valueOf(item.get("id").asInt()));
            locationCountryDto.setCountryName(item.get("name").asText());
            locationCountryDto.setIso2(item.get("iso2").asText());
            locationCountryDtos.add(locationCountryDto);
        });

        return locationCountryDtos;
    }

    public List<LocationStateDto> locationStateByCountryDataToLocationResponseDto(Mono<String> stateData) throws JsonProcessingException {
        List<LocationStateDto> locationStateDtos = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = stateData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);

        JsonNode items = jsonNode;

        items.forEach((item) -> {
            LocationStateDto locationStateDto = new LocationStateDto();
            locationStateDto.setStateId(String.valueOf(item.get("id").asInt()));
            locationStateDto.setStateName(item.get("name").asText());
            locationStateDtos.add(locationStateDto);
        });

        return locationStateDtos;
    }
}
