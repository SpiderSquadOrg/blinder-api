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
public class UpdateCharacteristicsRequestDto {

    @NotBlank
    private String userId;

    @MinListSize(minSize = 3, message = "Musics list must have at least three elements")
    private List<Music> musics;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<MusicCategory> musicCategories;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<Movie> movies;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<MovieCategory> movieCategories;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<TVSeries> tvSeries;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<TVSeriesCategory> tvSeriesCategories;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<Hobby> hobbies;

    /*@MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<Book> books;

    @MinListSize(minSize = 3, message = "Books list must have at least three elements")
    private List<BookCategory> bookCategories;*/
}
