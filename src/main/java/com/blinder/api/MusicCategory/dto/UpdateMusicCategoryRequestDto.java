package com.blinder.api.MusicCategory.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateMusicCategoryRequestDto {
    @NotBlank
    String name;
}
