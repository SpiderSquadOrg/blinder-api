package com.blinder.api.characteristics.controller;

import com.blinder.api.Music.dto.CreateMusicRequestDto;
import com.blinder.api.Music.mapper.MusicMapper;
import com.blinder.api.MusicCategory.dto.CreateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.mapper.MusicCategoryMapper;
import com.blinder.api.characteristics.dto.CharacteristicsResponseDto;
import com.blinder.api.characteristics.dto.CreateCharacteristicsRequestDto;
import com.blinder.api.characteristics.dto.UpdateCharacteristicsRequestDto;
import com.blinder.api.characteristics.mapper.CharacteristicsMapper;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.service.CharacteristicsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/characteristics")
@RestController
@RequiredArgsConstructor
public class CharacteristicsController {
    private final CharacteristicsService characteristicsService;

    @GetMapping
    @Operation(summary = "Get all characteristics")
    public ResponseEntity<Page<CharacteristicsResponseDto>> getAllCharacteristics(@RequestParam(name = "page", required = false) Integer page,
                                                                                  @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristicsService.getCharacteristics(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get characteristics by userId")
    public ResponseEntity<CharacteristicsResponseDto> getCharacteristicsByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristicsService.getCharacteristicsByUserId(userId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new characteristics")
    public ResponseEntity<CreateCharacteristicsRequestDto> addReport(@RequestBody CreateCharacteristicsRequestDto createCharacteristicsRequestDto) {
        characteristicsService.addCharacteristics(CharacteristicsMapper.INSTANCE.createCharacteristicsRequestDtoToCharacteristics(createCharacteristicsRequestDto));
        return new ResponseEntity<>(createCharacteristicsRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{characteristicsId}")
    @Operation(summary = "Update characteristics")
    public ResponseEntity<UpdateCharacteristicsRequestDto> updateCharacteristics(@PathVariable(name = "characteristicsId") String characteristicsId,
                                                                        @RequestBody UpdateCharacteristicsRequestDto updateCharacteristicsRequestDto) {
        characteristicsService.updateCharacteristics(characteristicsId, CharacteristicsMapper.INSTANCE.updateCharacteristicsRequestDtoToCharacteristics(updateCharacteristicsRequestDto));
        return new ResponseEntity<>(updateCharacteristicsRequestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{characteristicsId}")
    @Operation(summary = "Delete characteristics")
    public ResponseEntity<HttpStatus> deleteCharacteristics(@PathVariable(name = "characteristicsId") String characteristicsId) {
        characteristicsService.deleteCharacteristics(characteristicsId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/musics/{userId}")
    @Operation(summary = "Add music to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMusicList(@PathVariable String userId, @RequestBody CreateMusicRequestDto createMusicRequestDto){
        Characteristics characteristics = characteristicsService.addToMusicList(userId, MusicMapper.INSTANCE.createMusicRequestDtoToMusic(createMusicRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/musics/categories/{userId}")
    @Operation(summary = "Add music category to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMusicCategoryList(@PathVariable String userId, @RequestBody CreateMusicCategoryRequestDto createMusicCategoryRequestDto){
        Characteristics characteristics = characteristicsService.addToMusicCategoryList(userId, MusicCategoryMapper.INSTANCE.createMusicCategoryRequestDtoToMusicCategory(createMusicCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/movies/{userId}")
    @Operation(summary = "Add movie to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMovieList(@PathVariable String userId, @RequestBody CreateMovieRequestDto createMovieRequestDto){
        Characteristics characteristics = characteristicsService.addToMovieList(userId, MovieMapper.INSTANCE.createMovieRequestDtoToMovie(createMovieRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/movies/categories/{userId}")
    @Operation(summary = "Add movie category to user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> addToMovieCategoryList(@PathVariable String userId, @RequestBody CreateMovieCategoryRequestDto createMovieCategoryRequestDto){
        Characteristics characteristics = characteristicsService.addToMovieCategoryList(userId, MovieCategoryMapper.INSTANCE.createMovieCategoryRequestDtoToMusicCategory(createMovieCategoryRequestDto));
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.CREATED);
    }

    @PatchMapping("/musics/{musicId}/{userId}")
    @Operation(summary = "Remove music from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMusicList(@PathVariable String userId, @PathVariable String musicId){
        Characteristics characteristics = characteristicsService.removeFromMusicList(userId, musicId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/musics/categories/{musicCategoryId}/{userId}")
    @Operation(summary = "Remove music category from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMusicCategoryList(@PathVariable String userId, @PathVariable String musicCategoryId){
        Characteristics characteristics = characteristicsService.removeFromMusicCategoryList(userId, musicCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/movies/{movieId}/{userId}")
    @Operation(summary = "Remove movie from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMovieList(@PathVariable String userId, @PathVariable String movieId){
        Characteristics characteristics = characteristicsService.removeFromMovieList(userId, movieId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/movies/categories/{movieCategoryId}/{userId}")
    @Operation(summary = "Remove movie category from user's characteristics")
    public ResponseEntity<CharacteristicsResponseDto> removeFromMovieCategoryList(@PathVariable String userId, @PathVariable String movieCategoryId){
        Characteristics characteristics = characteristicsService.removeFromMovieCategoryList(userId, movieCategoryId);
        return new ResponseEntity<>(CharacteristicsMapper.INSTANCE.characteristicsToCharacteristicsResponseDto(characteristics), HttpStatus.NO_CONTENT);
    }
}
