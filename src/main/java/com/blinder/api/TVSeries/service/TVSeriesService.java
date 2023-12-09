package com.blinder.api.TVSeries.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

public interface TVSeriesService {
    Mono<String> searchTVSeries(String tvSeriesName, int limit) throws JsonProcessingException;
}
