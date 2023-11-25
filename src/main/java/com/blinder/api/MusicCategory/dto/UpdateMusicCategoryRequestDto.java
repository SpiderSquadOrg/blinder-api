package com.blinder.api.MusicCategory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMusicCategoryRequestDto {
    @NotBlank
    String name;
}
