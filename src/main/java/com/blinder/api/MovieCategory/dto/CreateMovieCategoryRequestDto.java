package com.blinder.api.MovieCategory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieCategoryRequestDto {
    @NotBlank
    private String name;
}
