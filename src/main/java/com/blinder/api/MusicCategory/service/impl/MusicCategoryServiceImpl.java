package com.blinder.api.MusicCategory.service.impl;

import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.MusicCategory.rules.MusicCategoryBusinessRules;
import com.blinder.api.MusicCategory.service.MusicCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class MusicCategoryServiceImpl implements MusicCategoryService {
    private final MusicCategoryRepository musicCategoryRepository;
    private final MusicCategoryBusinessRules musicCategoryBusinessRules;

    @Override
    public Page<MusicCategory> getMusicCategories(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.musicCategoryRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public MusicCategory addMusicCategory(MusicCategory musicCategory) {
        musicCategoryBusinessRules.checkIfMusicCategoryNameExists(musicCategory.getName());
        return musicCategoryRepository.save(musicCategory);
    }

    @Override
    public MusicCategory updateMusicCategory(String musicCategoryId, MusicCategory musicCategory) {
        musicCategoryBusinessRules.checkIfMusicCategoryNameExists(musicCategory.getName());
        MusicCategory musicCategoryToUpdate = this.musicCategoryRepository.findById(musicCategoryId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(musicCategory);

        BeanUtils.copyProperties(musicCategory, musicCategoryToUpdate, nullPropertyNames.toArray(new String[0]));

        this.musicCategoryRepository.save(musicCategoryToUpdate);
        return musicCategoryToUpdate;
    }

    @Override
    public void deleteMusicCategory(String musicCategoryId) {
        this.musicCategoryRepository.deleteById(musicCategoryId);
    }

    @Override
    public MusicCategory getMusicCategoryById(String musicCategoryId) {
        return this.musicCategoryRepository.findById(musicCategoryId).orElseThrow();
    }
}
