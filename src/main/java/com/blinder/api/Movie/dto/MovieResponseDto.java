package com.blinder.api.Movie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    private String moviesDatabaseId;
    private String name;
    private String year;
    private String image;

}