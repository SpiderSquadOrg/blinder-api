package com.blinder.api.Music.controller;

import com.blinder.api.Music.dto.CreateMusicRequestDto;
import com.blinder.api.Music.dto.MusicResponseDto;
import com.blinder.api.Music.dto.UpdateMusicRequestDto;
import com.blinder.api.Music.mapper.MusicMapper;
import com.blinder.api.Music.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/musics")
@RestController
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    @GetMapping
    @Operation(summary = "Get all musics")
    public ResponseEntity<Page<MusicResponseDto>> getAllMusics(@RequestParam(name = "page", required = false) Integer page,
                                                                                @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(MusicMapper.INSTANCE.musicToMusicResponseDto(musicService.getMusics(page, size)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new music ")
    public ResponseEntity<CreateMusicRequestDto> addMusic(@RequestBody CreateMusicRequestDto createMusicRequestDto) {
        musicService.addMusic(MusicMapper.INSTANCE.createMusicRequestDtoToMusic(createMusicRequestDto));
        return new ResponseEntity<>(createMusicRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update music ")
    public ResponseEntity<UpdateMusicRequestDto> updateMusic(@PathVariable(name = "id") String id,
                                                                             @RequestBody UpdateMusicRequestDto updateMusicRequestDto) {
        musicService.updateMusic(id, MusicMapper.INSTANCE.updateMusicRequestDtoToMusic(updateMusicRequestDto));
        return new ResponseEntity<>(updateMusicRequestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete music ")
    public ResponseEntity<HttpStatus> deleteMusic(@PathVariable(name = "id") String id) {
        musicService.deleteMusic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get music by id")
    public ResponseEntity<MusicResponseDto> getMusicById(@PathVariable String id) {
        return new ResponseEntity<>(MusicMapper.INSTANCE.musicToMusicResponseDto(musicService.getMusicById(id)), HttpStatus.OK);
    }
}
