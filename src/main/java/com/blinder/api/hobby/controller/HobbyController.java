package com.blinder.api.hobby.controller;

import com.blinder.api.hobby.dto.CreateHobbyRequestDto;
import com.blinder.api.hobby.dto.HobbyResponseDto;
import com.blinder.api.hobby.dto.UpdateHobbyRequestDto;
import com.blinder.api.hobby.mapper.HobbyMapper;
import com.blinder.api.hobby.service.HobbyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/hobbies")
@RestController
@RequiredArgsConstructor
public class HobbyController {
private final HobbyService hobbyService;

    @GetMapping
    @Operation(summary = "Get all hobbies")
    public ResponseEntity<Page<HobbyResponseDto>> getAllHobbies(@RequestParam(name = "page", required = false) Integer page,
                                                                 @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(HobbyMapper.INSTANCE.hobbyToHobbyResponseDto(hobbyService.getHobbies(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{hobbyId}")
    @Operation(summary = "Get hobby by id")
    public ResponseEntity<HobbyResponseDto> getHobbyById(@PathVariable String hobbyId) {
        return new ResponseEntity<>(HobbyMapper.INSTANCE.hobbyToHobbyResponseDto(hobbyService.getHobbyById(hobbyId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new hobby")
    public ResponseEntity<CreateHobbyRequestDto> addHobby(@RequestBody @Valid  CreateHobbyRequestDto createHobbyRequestDto) {
        hobbyService.addHobby(HobbyMapper.INSTANCE.createHobbyRequestDtoToHobby(createHobbyRequestDto));
        return new ResponseEntity<>(createHobbyRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{hobbyId}")
    @Operation(summary = "Update hobby")
    public ResponseEntity<UpdateHobbyRequestDto> updateHobby(@PathVariable(name = "hobbyId") String hobbyId,
                                                               @RequestBody
                                                               @Valid UpdateHobbyRequestDto updateHobbyRequestDto) {
        hobbyService.updateHobby(hobbyId, HobbyMapper.INSTANCE.updateHobbyRequestDtoToHobby(updateHobbyRequestDto));
        return new ResponseEntity<>(updateHobbyRequestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{hobbyId}")
    @Operation(summary = "Delete hobby")
    public ResponseEntity<HttpStatus> deleteHobby(@PathVariable(name = "hobbyId") String hobbyId) {
        hobbyService.deleteHobby(hobbyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}