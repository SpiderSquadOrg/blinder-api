package com.blinder.api.Movie.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MovieRequestDto {
    @NotBlank
    @NotNull
    private String imdbId;
    private String name;
    private int year;
    private String image;

}
