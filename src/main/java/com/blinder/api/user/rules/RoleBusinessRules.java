package com.blinder.api.user.rules;

import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.user.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleBusinessRules {
    private final RoleRepository roleRepository;

    public void checkIfNameExists(String name) {
        if (this.roleRepository.existsByName(name)) {
            throw new AlreadyExistsException("Name already exists");
        }
    }
}
