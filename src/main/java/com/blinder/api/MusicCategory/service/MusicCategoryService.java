package com.blinder.api.MusicCategory.service;

import com.blinder.api.MusicCategory.model.MusicCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

public interface MusicCategoryService {
    Mono<String> getMusicCategories() throws JsonProcessingException;

}
