package com.blinder.api.characteristics.model;

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
    private List<Hobby> hobbies;
    @OneToMany
    private List<Movie> movies;
    @OneToMany
    private List<MovieCategory> movieCategories;
    @OneToMany
    private List<TVSeries> tvSeries;
    @OneToMany
    private List<TVSeriesCategory> tvSeriesCategories;
    @OneToMany
    private List<Music> musics;
    @OneToMany
    private List<MusicCategory> musicCategories;
    @OneToMany
    private List<Book> books;
    @OneToMany
    private List<BookCategory> bookCategories;
}


