package com.blinder.api.MusicCategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateMusicCategoryRequestDto {
    @Size
    @NotBlank
    private String name;
}
