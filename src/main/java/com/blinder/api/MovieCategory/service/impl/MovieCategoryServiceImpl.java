package com.blinder.api.MovieCategory.service.impl;

import com.blinder.api.MovieCategory.service.MovieCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class MovieCategoryServiceImpl implements MovieCategoryService {

    @Override
    public Mono<String> getMovieCategories() throws JsonProcessingException {
        WebClient webClient = WebClient.create("https://api.example.com");
        String response = webClient.get()
                .uri("https://moviesdatabase.p.rapidapi.com/titles/utils/genres")
                .header("X-RapidAPI-Key", "a0c9dda4cbmsh4a1ac17b75dedbap1e633djsn1777b2f7576b")
                .header("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return Mono.just(response);
    }
}