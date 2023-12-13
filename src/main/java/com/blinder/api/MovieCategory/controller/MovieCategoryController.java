package com.blinder.api.MovieCategory.controller;

import com.blinder.api.MovieCategory.mapper.MovieCategoryCustomMapper;
import com.blinder.api.MovieCategory.service.MovieCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/movieCategories")
@RestController
@RequiredArgsConstructor
public class MovieCategoryController {
    private final MovieCategoryService movieCategoryService;
    private final MovieCategoryCustomMapper movieCategoryCustomMapper;

    @GetMapping
    @Operation(summary = "Get all movie categories")
    public ResponseEntity<List<String>> getMovieCategories() throws JsonProcessingException {
        return new ResponseEntity<>(movieCategoryCustomMapper.movieCategoryDataToList(movieCategoryService.getMovieCategories()), HttpStatus.OK);
    }
}