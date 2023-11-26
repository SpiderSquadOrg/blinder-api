package com.blinder.api.Music.rules;

import com.blinder.api.Music.repository.MusicRepository;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.exception.NotExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MusicBusinessRules {
    private final MusicCategoryRepository musicCategoryRepository;
    private final MusicRepository musicRepository;

    public void checkIfNotMusicCategoryExists(String id) {
        if (!this.musicCategoryRepository.existsById(id)) {
            throw new NotExistsException("Music category does not exist");
        }
    }

    public void checkIfMusicNameExists(String name) {
        if (this.musicRepository.existsByName(name)) {
            throw new AlreadyExistsException("This music name already exists");
        }
    }
}
