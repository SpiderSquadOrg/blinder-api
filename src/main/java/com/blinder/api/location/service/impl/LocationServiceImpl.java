package com.blinder.api.location.service.impl;

import com.blinder.api.location.model.Location;
import com.blinder.api.location.repository.LocationRepository;
import com.blinder.api.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Override
    public Mono<String> getAllCountries() {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://api.countrystatecity.in/v1/countries")
                .header("X-CSCAPI-KEY", "NWRsOExDZUU4ZVJid0N4RG5lUGFhcHEwS0pwWTFtMDdPTnZIamdIVQ==")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Mono.just(response);
    }

    @Override
    public Mono<String> getStatesByCountry(String iso2) {
        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://api.countrystatecity.in/v1/countries/" + iso2 + "/states")
                .header("X-CSCAPI-KEY", "NWRsOExDZUU4ZVJid0N4RG5lUGFhcHEwS0pwWTFtMDdPTnZIamdIVQ==")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Mono.just(response);
    }
}