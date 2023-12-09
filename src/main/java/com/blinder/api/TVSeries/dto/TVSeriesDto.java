package com.blinder.api.TVSeries.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TVSeriesDto {
    private String imdbId;
    private String name;
    private int year;
    private String image;
}
