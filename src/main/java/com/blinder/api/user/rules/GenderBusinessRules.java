package com.blinder.api.user.rules;

import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.repository.GenderRepository ;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenderBusinessRules {
    private final GenderRepository genderRepository;

    public void checkIfNameExists(String name) {
        if (this.genderRepository.existsByName(name)) {
            throw new AlreadyExistsException("Name already exists");
        }
    }
}
