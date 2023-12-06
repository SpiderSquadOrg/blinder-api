package com.blinder.api.characteristics.service.impl;

import com.blinder.api.Movie.model.Movie;
import com.blinder.api.Movie.repository.MovieRepository;
import com.blinder.api.Music.model.Music;
import com.blinder.api.Music.repository.MusicRepository;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.repository.CharacteristicsRepository;
import com.blinder.api.characteristics.service.CharacteristicsService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class CharacteristicsServiceImpl implements CharacteristicsService {
    private final CharacteristicsRepository characteristicsRepository;
    private final MusicCategoryRepository musicCategoryRepository;
    //private final MovieCategoryRepository movieCategoryRepository;
    private final MusicRepository musicRepository;
    private final MovieRepository movieRepository;

    @Override
    public Characteristics addCharacteristics(Characteristics characteristics) {
        List<Music> musics = new ArrayList<>();

        characteristics.getMusics().forEach((music) -> {
            Music newMusic = musicRepository.save(music);
            musics.add(newMusic);
        });
        characteristics.setMusics(musics);
        return characteristicsRepository.save(characteristics);
    }

    @Override
    public Page<Characteristics> getCharacteristics(Integer page, Integer size) {
        boolean isPageble = Objects.nonNull(page) && Objects.nonNull(size);

        if(!isPageble){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.characteristicsRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Characteristics getCharacteristicsByUserId(String userId) {
        return this.characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
    }

    @Override
    public Characteristics updateCharacteristics(String characteristicsId, Characteristics characteristics) {
        Characteristics characteristicsToUpdate = this.characteristicsRepository.findById(characteristicsId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(characteristics);

        BeanUtils.copyProperties(characteristics, characteristicsToUpdate, nullPropertyNames.toArray(new String[0]));

        this.characteristicsRepository.save(characteristicsToUpdate);
        return characteristicsToUpdate;
    }

    @Override
    public void deleteCharacteristics(String characteristicsId) {
        this.characteristicsRepository.deleteById(characteristicsId);
    }

    @Override
    public Characteristics addToMusicList(String userId, Music music) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        Music addedMusic = musicRepository.save(music);
        characteristics.addToMusicList(addedMusic);
        characteristicsRepository.save(characteristics);
        return characteristics;
    }

    @Override
    public Characteristics addToMusicCategoryList(String userId, MusicCategory musicCategory) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        MusicCategory addedMusicCategory = musicCategoryRepository.save(musicCategory);
        characteristics.addToMusicCategoryList(addedMusicCategory);
        characteristicsRepository.save(characteristics);
        return characteristics;
    }

    @Override
    public Characteristics addToMovieList(String userId, Movie movie) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        Movie addedMovie = movieRepository.save(movie);
        characteristics.addToMovieList(addedMovie);
        characteristicsRepository.save(characteristics);
        return characteristics;
    }

    /*
    @Override
    public Characteristics addToMovieCategoryList(String userId, MovieCategory movieCategory) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        MovieCategory addedMovieCategory = movieCategoryRepository.save(movieCategory);
        characteristics.addToMovieCategoryList(addedMovieCategory);
        characteristicsRepository.save(characteristics);
        return characteristics;
    }*/

    @Override
    public Characteristics removeFromMusicList(String userId, String musicId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        Music removedMusic = musicRepository.findById(musicId).orElseThrow();
        characteristics.removeFromMusicList(removedMusic);
        characteristicsRepository.save(characteristics);
        musicRepository.deleteById(musicId);
        return characteristics;
    }

    @Override
    public Characteristics removeFromMusicCategoryList(String userId, String musicCategoryId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        MusicCategory removedMusicCategory = musicCategoryRepository.findById(musicCategoryId).orElseThrow();
        characteristics.removeFromMusicCategoryList(removedMusicCategory);
        characteristicsRepository.save(characteristics);
        musicCategoryRepository.deleteById(musicCategoryId);
        return characteristics;
    }

    @Override
    public Characteristics removeFromMovieList(String userId, String movieId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        Movie removedMovie = movieRepository.findById(movieId).orElseThrow();
        characteristics.removeFromMovieList(removedMovie);
        characteristicsRepository.save(characteristics);
        movieRepository.deleteById(movieId);
        return characteristics;
    }

    /*
    @Override
    public Characteristics removeFromMovieCategoryList(String userId, String movieCategoryId) {
        Characteristics characteristics = characteristicsRepository.findCharacteristicsByUserId(userId).orElseThrow();
        MovieCategory removedMovieCategory = musicCategoryRepository.findById(movieCategoryId).orElseThrow();
        characteristics.removeFromMovieCategoryList(removedMovieCategory);
        characteristicsRepository.save(characteristics);
        movieCategoryRepository.deleteById(movieCategoryId);
        return characteristics;
    }*/
}
