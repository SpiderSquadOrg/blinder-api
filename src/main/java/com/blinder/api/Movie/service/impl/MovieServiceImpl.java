package com.blinder.api.Movie.service.impl;

import com.blinder.api.Movie.service.MovieService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor

public class MovieServiceImpl implements MovieService {

    @Value("${rapidapi.key}")
    private String api_key;
    @Override
    public Mono<String> searchMovie(String movieName, int limit) throws JsonProcessingException {

        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://moviesdatabase.p.rapidapi.com/titles/search/title/" + movieName + "?exact=false&titleType=movie&limit=" + limit)
                .header("X-RapidAPI-Key", api_key)
                .header("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Mono.just(response);
    }
}