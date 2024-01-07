package com.blinder.api.TVSeries.controller;

import com.blinder.api.TVSeries.dto.TVSeriesResponseDto;
import com.blinder.api.TVSeries.mapper.TVSeriesCustomMapper;
import com.blinder.api.TVSeries.service.TVSeriesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/tvSeries")
@RestController
@RequiredArgsConstructor
public class TVSeriesController {
    private final TVSeriesService tvSeriesService;
    private final TVSeriesCustomMapper tvSeriesCustomMapper;

    @GetMapping("/search")
    public ResponseEntity<List<TVSeriesResponseDto>> searchTVSeries(@RequestParam(name = "tvSeriesName") String tvSeriesName, @RequestParam(name = "limit", defaultValue = "50") int limit) throws JsonProcessingException {
        return new ResponseEntity<>(tvSeriesCustomMapper.tvSeriesDataToTvSeriesResponseDto(tvSeriesService.searchTVSeries(tvSeriesName, limit)), HttpStatus.OK);
    }
}
