package com.blinder.api.Music.service.impl;

import com.blinder.api.Music.model.Music;
import com.blinder.api.Music.repository.MusicRepository;
import com.blinder.api.Music.rules.MusicBusinessRules;
import com.blinder.api.Music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class MusicServiceImpl implements MusicService {
    private final MusicRepository musicRepository;
    private final MusicBusinessRules musicBusinessRules;

    @Override
    public Page<Music> getMusics(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.musicRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Music addMusic(Music music) {
        musicBusinessRules.checkIfNotMusicCategoryExists(music.getMusicCategory().getId());
        musicBusinessRules.checkIfMusicNameExists(music.getName());
        return musicRepository.save(music);
    }

    @Override
    public Music updateMusic(String musicId, Music music) {
        musicBusinessRules.checkIfMusicNameExists(music.getName());

        if (music.getMusicCategory().getId()==null) music.setMusicCategory(null);

        Music musicToUpdate = this.musicRepository.findById(musicId).orElseThrow();
        Set<String> nullPropertyNames = getNullPropertyNames(music);
        BeanUtils.copyProperties(music, musicToUpdate, nullPropertyNames.toArray(new String[0]));

        this.musicRepository.save(musicToUpdate);
        return musicToUpdate;
    }

    @Override
    public void deleteMusic(String musicId) {
        this.musicRepository.deleteById(musicId);
    }

    @Override
    public Music getMusicById(String musicId) {
        return this.musicRepository.findById(musicId).orElseThrow();
    }
}