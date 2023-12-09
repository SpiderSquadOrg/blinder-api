package com.blinder.api.characteristics.dto;

import com.blinder.api.Movie.model.Movie;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.common.annotations.MinListSize;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.user.model.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CharacteristicsResponseDto {

    private String userId;
    private List<Music> musics;
    private List<MusicCategory> musicCategories;
    private List<Movie> movies;
    private List<MovieCategory> movieCategories;
    private List<TVSeries> tvSeries;
    private List<TVSeriesCategory> tvSeriesCategories;
    private List<Hobby> hobbies;

    /*private List<Book> books;
    private List<BookCategory> bookCategories;*/
}
