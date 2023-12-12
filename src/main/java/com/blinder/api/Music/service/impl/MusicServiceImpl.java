package com.blinder.api.Music.service.impl;

import com.blinder.api.Music.service.MusicService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {

    @Value("${spotify.client_id}")
    private String clientId;

    @Value("${spotify.client_secret}")
    private String clientSecret;


    public Mono<String> accessToken() throws JsonProcessingException {

        WebClient webClient = WebClient.create("https://api.example.com");

        String jsonString = webClient.post()
                .uri("https://accounts.spotify.com/api/token")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes()))
                .header("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .bodyValue(String.format("grant_type=client_credentials&client_id=%s&client_secret=%s", clientId, clientSecret))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonString);
        String token = jsonNode.get("access_token").asText();
        return Mono.just(token);
    }

    @Override
    public Mono<String> searchMusic(String musicName, int limit) throws JsonProcessingException {

        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://api.spotify.com/v1/search?q=" + musicName + "&type=track&limit=" + limit)
                .header("Authorization", "Bearer " + accessToken().block())
                .retrieve()
                .bodyToMono(String.class)
                .block();


        return Mono.just(response);
    }

    public Mono<String> getMusicById(String id) throws JsonProcessingException {

        WebClient webClient = WebClient.create("https://api.example.com");

        String response = webClient.get()
                .uri("https://api.spotify.com/v1/tracks/" + id)
                .header("Authorization", "Bearer " + accessToken().block())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return Mono.just(response);
    }
}