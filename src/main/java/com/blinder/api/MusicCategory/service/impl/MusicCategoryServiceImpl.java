package com.blinder.api.MusicCategory.service.impl;

import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
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
        return musicCategoryRepository.save(musicCategory);
    }

    @Override
    public MusicCategory updateMusicCategory(String musicCategoryId, MusicCategory musicCategory) {
        MusicCategory reportToUpdate = this.musicCategoryRepository.findById(musicCategoryId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(musicCategory);

        BeanUtils.copyProperties(musicCategory, reportToUpdate, nullPropertyNames.toArray(new String[0]));

        this.musicCategoryRepository.save(reportToUpdate);
        return reportToUpdate;
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
