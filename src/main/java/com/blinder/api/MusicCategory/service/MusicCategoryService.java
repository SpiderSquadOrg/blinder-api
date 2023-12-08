package com.blinder.api.MusicCategory.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface MusicCategoryService {
    Mono<String> getMusicCategories() throws JsonProcessingException;

}
