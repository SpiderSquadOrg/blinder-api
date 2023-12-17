package com.blinder.api.Music.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicRequestDto {
    @NotBlank
    @NotNull
    private String spotifyId;
    private String name;
    private String artist;
    private String album;
    private String image;
}
