package com.blinder.api.MusicCategory.controller;

import com.blinder.api.MusicCategory.dto.CreateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.dto.MusicCategoryResponseDto;
import com.blinder.api.MusicCategory.dto.UpdateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.mapper.MusicCategoryMapper;
import com.blinder.api.MusicCategory.service.MusicCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/musicCategories")
@RestController
@RequiredArgsConstructor
public class MusicCategoryController {
    private final MusicCategoryService musicCategoryService;

    @GetMapping
    @Operation(summary = "Get all music categories")
    public ResponseEntity<Page<MusicCategoryResponseDto>> getAllMusicCategories(@RequestParam(name = "page", required = false) Integer page,
                                                                        @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(MusicCategoryMapper.INSTANCE.musicCategoryToMusicCategoryResponseDto(musicCategoryService.getMusicCategories(page, size)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new music category")
    public ResponseEntity<CreateMusicCategoryRequestDto> addMusicCategory(@RequestBody CreateMusicCategoryRequestDto createMusicCategoryRequestDto) {
        musicCategoryService.addMusicCategory(MusicCategoryMapper.INSTANCE.createMusicCategoryRequestDtoToMusicCategory(createMusicCategoryRequestDto));
        return new ResponseEntity<>(createMusicCategoryRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update music category")
    public ResponseEntity<UpdateMusicCategoryRequestDto> updateMusicCategory(@PathVariable(name = "id") String id,
                                                               @RequestBody UpdateMusicCategoryRequestDto updateMusicCategoryRequestDto) {
        musicCategoryService.updateMusicCategory(id, MusicCategoryMapper.INSTANCE.updateMusicCategoryRequestDtoToMusicCategory(updateMusicCategoryRequestDto));
        return new ResponseEntity<>(updateMusicCategoryRequestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete music category")
    public ResponseEntity<HttpStatus> deleteMusicCategory(@PathVariable(name = "id") String id) {
        musicCategoryService.deleteMusicCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get music category by id")
    public ResponseEntity<MusicCategoryResponseDto> getMusicCategoryById(@PathVariable String id) {
        return new ResponseEntity<>(MusicCategoryMapper.INSTANCE.musicCategoryToMusicCategoryResponseDto(musicCategoryService.getMusicCategoryById(id)), HttpStatus.OK);
    }
}