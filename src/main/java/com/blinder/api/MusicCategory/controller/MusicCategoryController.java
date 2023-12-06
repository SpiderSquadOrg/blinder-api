package com.blinder.api.MusicCategory.controller;

import com.blinder.api.MusicCategory.mapper.MusicCategoryCustomMapper;
import com.blinder.api.MusicCategory.service.MusicCategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/musicCategories")
@RestController
@RequiredArgsConstructor
public class MusicCategoryController {
    private final MusicCategoryService musicCategoryService;
    private final MusicCategoryCustomMapper musicCategoryCustomMapper;

    @GetMapping
    @Operation(summary = "Get all music categories")
    public ResponseEntity<JsonNode> getMusicCategories() throws JsonProcessingException {
        return new ResponseEntity<>(musicCategoryCustomMapper.musicCategoryDataToList(musicCategoryService.getMusicCategories()), HttpStatus.OK);
    }
}