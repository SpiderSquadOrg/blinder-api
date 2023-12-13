package com.blinder.api.MusicCategory.service.impl;

import com.blinder.api.Music.service.MusicService;
import com.blinder.api.Music.service.impl.MusicServiceImpl;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.MusicCategory.rules.MusicCategoryBusinessRules;
import com.blinder.api.MusicCategory.service.MusicCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class MusicCategoryServiceImpl implements MusicCategoryService {

    private final MusicServiceImpl musicService;

    @Override
    public Mono<String> getMusicCategories() throws JsonProcessingException {
        WebClient webClient = WebClient.create("https://api.example.com");
                String response = webClient.get()
                        .uri("https://api.spotify.com/v1/recommendations/available-genre-seeds")
                        .header("Authorization", "Bearer " + musicService.accessToken().block())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

        return Mono.just(response);
    }
}