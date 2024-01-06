package com.blinder.api.characteristics.rules;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CharacteristicsBusinessRules {

    //Music
    public void checkIfMusicAlreadyExistsInList(Characteristics characteristics, String spotifyId) {
        if (characteristics.getMusics().stream().anyMatch(existingMusic -> existingMusic.getSpotifyId().equals(spotifyId))) {
            throw new AlreadyExistsException("This music is already in your list");
        }
    }

    public void checkIfMusicExistsInList(Characteristics characteristics, String spotifyId) {
        if (characteristics.getMusics().stream().noneMatch(existingMusic -> existingMusic.getSpotifyId().equals(spotifyId))) {
            throw new NotExistsException("Music couldn't find in your list");
        }
    }

    //Music category
    public void checkIfMusicCategoryAlreadyExistsInList(Characteristics characteristics, String name) {
        if (characteristics.getMusicCategories().stream().anyMatch(existingMusicCategory -> existingMusicCategory.getName().equals(name))) {
            throw new AlreadyExistsException("This music category is already in your list");
        }
    }

    public void checkIfMusicCategoryExistsInList(Characteristics characteristics, String musicId) {
        if (characteristics.getMusicCategories().stream().noneMatch(existingMusicCategory -> existingMusicCategory.getId().equals(musicId))) {
            throw new NotExistsException("Music category couldn't find in your list");
        }
    }

    //Movie
    public void checkIfMovieAlreadyExistsInList(Characteristics characteristics, String imdbId) {
        if (characteristics.getMovies().stream().anyMatch(existingMovie -> existingMovie.getImdbId().equals(imdbId))) {
            throw new AlreadyExistsException("This movie is already in your list");
        }
    }

    public void checkIfMovieExistsInList(Characteristics characteristics, String movieId) {
        if (characteristics.getMovies().stream().noneMatch(existingMovie -> existingMovie.getId().equals(movieId))) {
            throw new NotExistsException("Movie couldn't find in your list");
        }
    }

    //Movie category
    public void checkIfMovieCategoryAlreadyExistsInList(Characteristics characteristics, String name) {
        if (characteristics.getMovieCategories().stream().anyMatch(existingMovieCategory -> existingMovieCategory.getName().equals(name))) {
            throw new AlreadyExistsException("This movie category is already in your list");
        }
    }

    public void checkIfMovieCategoryExistsInList(Characteristics characteristics, String movieCategoryId) {
        if (characteristics.getMovieCategories().stream().noneMatch(existingMovieCategory -> existingMovieCategory.getId().equals(movieCategoryId))) {
            throw new NotExistsException("Movie category couldn't find in your list");
        }
    }

    //Tv series
    public void checkIfTvSeriesAlreadyExistsInList(Characteristics characteristics, String imdbId) {
        if (characteristics.getTvSeriesList().stream().anyMatch(existingTvSeries -> existingTvSeries.getImdbId().equals(imdbId))) {
            throw new AlreadyExistsException("This TV series is already in your list");
        }
    }

    public void checkIfTvSeriesExistsInList(Characteristics characteristics, String tvSeriesId) {
        if (characteristics.getTvSeriesList().stream().noneMatch(existingTvSeries -> existingTvSeries.getId().equals(tvSeriesId))) {
            throw new NotExistsException("TV series couldn't find in your list");
        }
    }

    //Tv series category
    public void checkIfTvSeriesCategoryAlreadyExistsInList(Characteristics characteristics, String name) {
        if (characteristics.getTvSeriesCategories().stream().anyMatch(existingTvSeriesCategory -> existingTvSeriesCategory.getName().equals(name))) {
            throw new AlreadyExistsException("This TV series category is already in your list");
        }
    }

    public void checkIfTvSeriesCategoryExistsInList(Characteristics characteristics, String tvSeriesCategoryId) {
        if (characteristics.getTvSeriesCategories().stream().noneMatch(existingTvSeriesCategory -> existingTvSeriesCategory.getId().equals(tvSeriesCategoryId))) {
            throw new NotExistsException("TV series category couldn't find in your list");
        }
    }

    //Hobby
    public void checkIfHobbyAlreadyExistsInList(Characteristics characteristics, String name) {
        if (characteristics.getHobbies().stream().anyMatch(existingHobby -> existingHobby.getName().equals(name))) {
            throw new AlreadyExistsException("This hobby is already in your list");
        }
    }

    public void checkIfHobbyExistsInList(Characteristics characteristics, String hobbyName) {
        if (characteristics.getHobbies().stream().noneMatch(existingHobby -> existingHobby.getName().equals(hobbyName))) {
            throw new NotExistsException("Hobby couldn't find in your list");
        }
    }
}