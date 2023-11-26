package com.blinder.api.Music.service;

import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import org.springframework.data.domain.Page;

public interface MusicService {
    Page<Music> getMusics(Integer page, Integer size);
    Music addMusic(Music music);
    Music updateMusic(String musicId, Music music);
    void deleteMusic(String musicId);
    Music getMusicById(String musicId);
}
