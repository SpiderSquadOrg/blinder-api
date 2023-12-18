package com.blinder.api.characteristics.dto;

import com.blinder.api.Movie.dto.MovieResponseDto;
import com.blinder.api.Movie.model.Movie;
import com.blinder.api.MovieCategory.dto.MovieCategoryResponseDto;
import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.Music.dto.MusicResponseDto;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.dto.MusicCategoryResponseDto;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.TVSeries.dto.TVSeriesResponseDto;
import com.blinder.api.TVSeries.model.TVSeries;
import com.blinder.api.TVSeriesCategories.dto.TVSeriesCategoryResponseDto;
import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
import com.blinder.api.hobby.dto.HobbyResponseDto;
import com.blinder.api.hobby.model.Hobby;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicsResponseDto {

    private String userId;
    private List<MusicResponseDto> musics;
    private List<MusicCategoryResponseDto> musicCategories;
    private List<MovieResponseDto> movies;
    private List<MovieCategoryResponseDto> movieCategories;
    private List<TVSeriesResponseDto> tvSeriesList;
    private List<TVSeriesCategoryResponseDto> tvSeriesCategories;
    private List<HobbyResponseDto> hobbies;

    /*private List<Book> books;
    private List<BookCategory> bookCategories;*/
}
