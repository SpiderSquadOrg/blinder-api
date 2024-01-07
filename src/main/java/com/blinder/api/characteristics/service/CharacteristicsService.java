package com.blinder.api.characteristics.service;

import com.blinder.api.Movie.model.Movie;
import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.TVSeries.model.TVSeries;
import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.hobby.model.Hobby;
import org.springframework.data.domain.Page;

public interface CharacteristicsService {

    Characteristics addCharacteristics(Characteristics characteristics);
    Page<Characteristics> getCharacteristics(Integer page, Integer size);
    Characteristics getCharacteristicsByUserId(String userId);
    Characteristics updateCharacteristics(String characteristicsId, Characteristics characteristics);
    void deleteCharacteristics(String characteristicsId);

    //Music and music category
    Characteristics addToMusicList(String userId, Music music);
    Characteristics addToMusicCategoryList(String userId, MusicCategory musicCategory);
    Characteristics removeFromMusicList(String userId, String spotifyId);
    Characteristics removeFromMusicCategoryList(String userId, String musicCategoryId);

    //Movie and movie category
    Characteristics addToMovieList(String userId, Movie movie);
    Characteristics addToMovieCategoryList(String userId, MovieCategory movieCategory);
    Characteristics removeFromMovieList(String userId, String movieId);
    Characteristics removeFromMovieCategoryList(String userId, String movieCategoryId);

    //Tv series and tv series category
    Characteristics addToTvSeriesList(String userId, TVSeries tvSeries);
    Characteristics addToTvSeriesCategoryList(String userId, TVSeriesCategory tvSeriesCategory);
    Characteristics removeFromTvSeriesList(String userId, String tvSeriesId);
    Characteristics removeFromTvSeriesCategoryList(String userId, String tvSeriesCategoryId);

    //Hobby
    Characteristics addToHobbyList(String userId, Hobby hobby);
    Characteristics removeFromHobbyList(String userId, String hobbyId);
}
