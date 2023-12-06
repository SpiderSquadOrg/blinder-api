package com.blinder.api.characteristics.model;

import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class Characteristics extends BaseEntity {
    @OneToOne
    private User user;
    @OneToMany
    private List<Movie> movies;
    @OneToMany
    private List<MovieCategory> movieCategories;
    /*@OneToMany
    private List<Hobby> hobbies;

    @OneToMany
    private List<TVSeries> tvSeries;
    @OneToMany
    private List<TVSeriesCategory> tvSeriesCategories;*/
    @OneToMany
    private List<Music> musics = new ArrayList<>();
    @OneToMany
    private List<MusicCategory> musicCategories = new ArrayList<>();
    /*@OneToMany
    private List<Book> books;
    @OneToMany
    private List<BookCategory> bookCategories;*/

    public void addToMusicList(Music music){
        musics.add(music);
    }
    public void addToMusicCategoryList(MusicCategory musicCategory){
        musicCategories.add(musicCategory);
    }

    public void addToMovieList(Movie movie){
        movies.add(movie);
    }
    public void addToMovieCategoryList(MovieCategory movieCategory){
        movieCategories.add(movieCategory);
    }

    public void removeFromMusicList(Music music){
        musics.remove(music);
    }
    public void removeFromMusicCategoryList(MusicCategory musicCategory){
        musicCategories.remove(musicCategory);
    }

    public void removeFromMovieList(Movie movie){
        movies.remove(movie);
    }
    public void removeFromMovieCategoryList(MovieCategory movieCategory){
        movieCategories.remove(movieCategory);
    }
}


