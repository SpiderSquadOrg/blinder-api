package com.blinder.api.Movie.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MovieServiceImpl {
    public Mono<String> searchMovie(String movieName) throws JsonProcessingException {

        WebClient webClient = WebClient.create("moviesdatabase.p.rapidapi.com");

        String response = webClient.get()
                .uri("https://moviesdatabase.p.rapidapi.com/titles/search/title/" + movieName + "?exact=false&titleType=movie")
                .header("X-RapidAPI-Key", "e32b9daab3msh7e93e84593079ebp17c5a1jsnaf1454535e70")
                .header("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return Mono.just(response);

    }
}
