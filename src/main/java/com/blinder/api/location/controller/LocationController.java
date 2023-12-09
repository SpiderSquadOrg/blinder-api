package com.blinder.api.location.controller;

import com.blinder.api.location.dto.LocationDto;
import com.blinder.api.location.mapper.LocationCustomMapper;
import com.blinder.api.location.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequestMapping("/locations")
@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;
    private final LocationCustomMapper locationCustomMapper;
    @GetMapping("/getAllCountries")
    public ResponseEntity<List<LocationDto>> getAllCountries() throws JsonProcessingException {
        return new ResponseEntity<>(locationCustomMapper.locationCountryDataToLocationResponseDto(locationService.getAllCountries()), HttpStatus.OK);
    }

    @GetMapping("/getStatesByCountry")
    public ResponseEntity<List<LocationDto>> getStatesByCountry(String iso2) throws JsonProcessingException {
        return new ResponseEntity<>(locationCustomMapper.locationCountryDataToLocationResponseDto(locationService.getStatesByCountry(iso2)), HttpStatus.OK);
    }
}
