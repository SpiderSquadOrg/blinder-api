package com.blinder.api.Music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MusicResponseDto {
    private String id;
    private String name;
    private List<String> artists;
    private String album;
    private String image;

}
