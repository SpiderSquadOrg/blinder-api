package com.blinder.api.MusicCategory.service;

import com.blinder.api.MusicCategory.model.MusicCategory;
import org.springframework.data.domain.Page;

public interface MusicCategoryService {
    Page<MusicCategory> getMusicCategories(Integer page, Integer size);
    MusicCategory addMusicCategory(MusicCategory musicCategory);
    MusicCategory updateMusicCategory(String musicCategoryId, MusicCategory musicCategory);
    void deleteMusicCategory(String musicCategoryId);
    MusicCategory getMusicCategoryById(String musicCategoryId);

}
