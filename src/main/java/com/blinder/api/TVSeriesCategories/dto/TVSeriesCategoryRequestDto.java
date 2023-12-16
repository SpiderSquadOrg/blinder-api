package com.blinder.api.TVSeriesCategories.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVSeriesCategoryRequestDto {
    @NotBlank
    private String name;
}