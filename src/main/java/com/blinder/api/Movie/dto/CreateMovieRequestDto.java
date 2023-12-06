package com.blinder.api.Movie.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateMovieRequestDto {
    @NotBlank
    @NotNull
    private String moviesDatabaseId;

}
