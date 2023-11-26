package com.blinder.api.Music.dto;

import com.blinder.api.MusicCategory.model.MusicCategory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMusicRequestDto {
    private String name;
    private String musicCategoryId;
}
