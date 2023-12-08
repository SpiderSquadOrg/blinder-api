package com.blinder.api.hobby.rules;

import com.blinder.api.Music.repository.MusicRepository;
import com.blinder.api.MusicCategory.repository.MusicCategoryRepository;
import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.exception.NotExistsException;
import com.blinder.api.hobby.repository.HobbyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HobbyBusinessRules {
    private final HobbyRepository hobbyRepository;


    public void checkIfHobbyNameExists(String name) {
        if (this.hobbyRepository.existsByName(name)) {
            throw new AlreadyExistsException("This hobby name already exists");
        }
    }
}
