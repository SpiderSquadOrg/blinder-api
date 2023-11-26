package com.blinder.api.MusicCategory.rules;

import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.exception.AlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MusicCategoryBusinessRules {
    private final MusicCategoryRepository musicCategoryRepository;


    public void checkIfMusicCategoryNameExists(String name) {
        if (this.musicCategoryRepository.existsByName(name)) {
            throw new AlreadyExistsException("This music category name already exists");
        }
    }
}
