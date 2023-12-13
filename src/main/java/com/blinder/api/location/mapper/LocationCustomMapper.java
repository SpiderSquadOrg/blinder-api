package com.blinder.api.location.mapper;

import com.blinder.api.location.dto.CreateLocationDto;
import com.blinder.api.location.dto.LocationCountryDto;
import com.blinder.api.location.dto.LocationStateDto;
import com.blinder.api.location.model.Location;
import com.blinder.api.location.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationCustomMapper {
    private final LocationService locationService;
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
            locationStateDto.setIso2(item.get("iso2").asText());
            locationStateDtos.add(locationStateDto);
        });

        return locationStateDtos;
    }

    public LocationCountryDto locationCountryDataToLocationResponseDtoById(Mono<String> countryData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = countryData.block();

        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode item = jsonNode;

        LocationCountryDto locationCountryDto = new LocationCountryDto();
        locationCountryDto.setCountryId(String.valueOf(item.get("id").asInt()));
        locationCountryDto.setCountryName(item.get("name").asText());
        locationCountryDto.setIso2(item.get("iso2").asText());

        return locationCountryDto;
    }

    public LocationStateDto locationStateByCountryDataToLocationResponseDtoById(Mono<String> stateData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = stateData.block();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        JsonNode item = jsonNode;

        LocationStateDto locationStateDto = new LocationStateDto();
        locationStateDto.setStateId(String.valueOf(item.get("id").asInt()));
        locationStateDto.setStateName(item.get("name").asText());
        locationStateDto.setIso2(item.get("iso2").asText());

        return locationStateDto;
    }

    public Location createLocationDtoToLocation(CreateLocationDto createLocationDto) throws JsonProcessingException {
        Location location = new Location();
        LocationCountryDto country =  locationCountryDataToLocationResponseDtoById(locationService.getCountryById(createLocationDto.getCountryIso2()));
        LocationStateDto state = locationStateByCountryDataToLocationResponseDtoById(locationService.getStateById(createLocationDto.getCountryIso2(), createLocationDto.getStateIso2()));

        location.setCountryId(country.getCountryId());
        location.setCountryIso2(country.getIso2());
        location.setCountryName(country.getCountryName());
        location.setStateId(state.getStateId());
        location.setStateIso2(state.getIso2());
        location.setStateName(state.getStateName());

        return location;
    }
}
