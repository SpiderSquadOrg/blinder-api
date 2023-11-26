package com.blinder.api.characteristics.controller;

import com.blinder.api.characteristics.dto.CharacteristicsResponseDto;
import com.blinder.api.characteristics.dto.CreateCharacteristicsRequestDto;
import com.blinder.api.characteristics.dto.UpdateCharacteristicsRequestDto;
import com.blinder.api.characteristics.mapper.CharacteristicsMapper;
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
    @Operation(summary = "Get report by userId")
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
}
