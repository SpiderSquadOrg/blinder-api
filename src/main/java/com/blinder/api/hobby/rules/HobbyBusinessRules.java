package com.blinder.api.hobby.rules;

import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.exception.NotExistsException;
import com.blinder.api.hobby.repository.HobbyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HobbyBusinessRules {
    private final HobbyRepository hobbyRepository;

    public void checkIfHobbyAlreadyExists(String name) {
        if (this.hobbyRepository.existsByName(name)) {
            throw new AlreadyExistsException("This hobby is already added");
        }
    }

    public void checkIfHobbyExistsById(String hobbyId) {
        if (!this.hobbyRepository.existsById(hobbyId)) {
            throw new NotExistsException("Hobby couldn't find");
        }
    }

    public void checkIfHobbyExistsByName(String name) {
        if (!this.hobbyRepository.existsByName(name)) {
            throw new NotExistsException("Hobby couldn't find");
        }
    }
}
