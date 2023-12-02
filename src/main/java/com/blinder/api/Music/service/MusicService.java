package com.blinder.api.Music.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface MusicService {
    Mono<String> searchMusic(String musicName, int limit) throws JsonProcessingException;
}
