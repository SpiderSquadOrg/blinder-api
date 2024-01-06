package com.blinder.api.user.controller;

import com.blinder.api.user.dto.GenderRequestDto;
import com.blinder.api.user.dto.GenderResponseDto;
import com.blinder.api.user.mapper.GenderMapper;
import com.blinder.api.user.service.GenderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/genders")
@RestController
@RequiredArgsConstructor
public class GenderController {
    private final GenderService genderService;

    @GetMapping
    @Operation(summary = "Get all genders")
    public ResponseEntity<Page<GenderResponseDto>> getAllGenders(@RequestParam(name = "page", required = false) Integer page,
                                                             @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(GenderMapper.INSTANCE.genderToGenderResponseDto(genderService.getGenders(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{genderId}")
    @Operation(summary = "Get gender by id")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<GenderResponseDto> getReportById(@PathVariable String genderId) {
        return new ResponseEntity<>(GenderMapper.INSTANCE.genderToGenderResponseDto(genderService.getGenderById(genderId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add gender")
    public ResponseEntity<GenderRequestDto> addGender(@RequestBody GenderRequestDto genderRequestDto) {
        this.genderService.addGender(GenderMapper.INSTANCE.genderRequestDtoToGender(genderRequestDto));
        return new ResponseEntity<>(genderRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{genderId}")
    @Operation(summary = "Update gender by id")
    public ResponseEntity<GenderResponseDto> updateGenderById(@PathVariable String genderId,
                                                          @RequestBody GenderRequestDto genderRequestDto) {
        return new ResponseEntity<>(GenderMapper.INSTANCE.genderToGenderResponseDto(this.genderService.updateGender(genderId, GenderMapper.INSTANCE.genderRequestDtoToGender(genderRequestDto))), HttpStatus.OK);
    }

    @DeleteMapping("/{genderId}")
    @Operation(summary = "Delete gender by id")
    public ResponseEntity<HttpStatus> deleteGenderById(@PathVariable String genderId) {
        this.genderService.deleteGender(genderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
