package com.blinder.api.characteristics.service;

import com.blinder.api.Movie.model.Movie;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.characteristics.model.Characteristics;
import org.springframework.data.domain.Page;

public interface CharacteristicsService {
    Characteristics addCharacteristics(Characteristics characteristics);

    Page<Characteristics> getCharacteristics(Integer page, Integer size);

    Characteristics getCharacteristicsByUserId(String userId);

    Characteristics updateCharacteristics(String characteristicsId, Characteristics characteristics);

    void deleteCharacteristics(String characteristicsId);

    Characteristics addToMusicList(String userId, Music music);
    Characteristics addToMusicCategoryList(String userId, MusicCategory musicCategory);

    Characteristics addToMovieList(String userId, Movie movie);
    //Characteristics addToMovieCategoryList(String userId, MovieCategory movieCategory);

    Characteristics removeFromMusicList(String userId, String musicId);
    Characteristics removeFromMusicCategoryList(String userId, String musicCategoryId);

    Characteristics removeFromMovieList(String userId, String movieId);
    Characteristics removeFromMovieCategoryList(String userId, String movieCategoryId);

}
