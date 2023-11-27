package com.blinder.api.user.rules;

import com.blinder.api.exception.AlreadyExistsException;
import com.blinder.api.exception.NotExistsException;
import com.blinder.api.user.repository.GenderRepository;
import com.blinder.api.user.repository.RoleRepository;
import com.blinder.api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserBusinessRules {
    private final UserRepository userRepository;
    private final GenderRepository genderRepository;
    private final RoleRepository roleRepository;

    public void checkIfEmailExists(String email) {
        if (this.userRepository.existsByEmail(email)) {
            throw new AlreadyExistsException("Email already exists");
        }
    }

    public void checkIfUsernameExists(String username) {
        if (this.userRepository.existsByUsername(username)) {
            throw new AlreadyExistsException("Username already exists");
        }
    }

    public void checkIfUserExists(String id) {
        if (!this.userRepository.existsById(id)) {
            throw new NotExistsException("User does not exist");
        }
    }

    public void checkIfUserIsBanned(String id) {
        if (this.userRepository.findById(id).orElseThrow().isBanned()) {
            throw new AlreadyExistsException("User is already banned");
        }
    }

    public void checkIfUserIsNotBanned(String id) {
        if (!this.userRepository.findById(id).orElseThrow().isBanned()) {
            throw new AlreadyExistsException("User is not banned");
        }
    }

    public void checkIfUserIsBlocked(String blockerId, String toBeBlockedId) {
        if (this.userRepository.findById(blockerId).orElseThrow().getBlockedUsers().contains(toBeBlockedId)) {
            throw new AlreadyExistsException("User is already blocked");
        }
    }

    public void checkIfUserIsNotBlocked(String blockerId, String toBeBlockedId) {
        if (!this.userRepository.findById(blockerId).orElseThrow().getBlockedUsers().contains(toBeBlockedId)) {
            throw new AlreadyExistsException("User is not blocked");
        }
    }

    public void checkIfGenderDoesNotExists(String genderId){
        if (!this.genderRepository.existsById(genderId)){
            throw new NotExistsException("Gender does not exist");
        }
    }

    public void checkIfRoleDoesNotExists(String roleId){
        if (!this.roleRepository.existsById(roleId)){
            throw new NotExistsException("Role does not exist");
        }
    }


}
