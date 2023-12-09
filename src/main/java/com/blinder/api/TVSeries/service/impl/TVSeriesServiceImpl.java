package com.blinder.api.TVSeries.service.impl;

import com.blinder.api.TVSeries.service.TVSeriesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TVSeriesServiceImpl implements TVSeriesService {
    @Value("${rapidapi.key}")
    private String api_key;
    @Override
    public Mono<String> searchTVSeries(String tvSeriesName, int limit) {

        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://moviesdatabase.p.rapidapi.com/titles/search/title/" + tvSeriesName + "?exact=false&titleType=tvSeries&limit=" + limit)
                .header("X-RapidAPI-Key", api_key)
                .header("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Mono.just(response);
    }
}
