package com.blinder.api.user.service.impl;

import com.blinder.api.location.model.Location;
import com.blinder.api.location.repository.LocationRepository;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.rules.UserBusinessRules;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserAuthService userAuthService;
    private final UserBusinessRules userBusinessRules;
    @Override
    public User getUserById(String id) {
        this.userBusinessRules.checkIfUserExists(id);
        return this.userRepository.findById(id).orElseThrow();
    }

    @Override
    public User addUser(User user) {
        Location newLocation = this.locationRepository.save(user.getLocation());
        User newUser = this.userRepository.save(user);
        newLocation.setUser(newUser);
        this.locationRepository.save(newLocation);
        return newUser;
    }

    @Override
    public void deleteUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userRepository.deleteById(userId);
    }

    @Override
    public User banUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsNotBanned(userId);

        User user = this.userRepository.findById(userId).orElseThrow();
        user.setBanned(true);
        return this.userRepository.save(user);
    }

    @Override
    public User unbanUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsBanned(userId);

        User user = this.userRepository.findById(userId).orElseThrow();
        user.setBanned(false);
        return this.userRepository.save(user);
    }

    @Override
    public User blockUserById(String userId) {
        String activeUserId = this.userAuthService.getActiveUser().getId();

        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsNotBlocked(activeUserId,userId);

        User user = this.userRepository.findById(activeUserId).orElseThrow();
        User toBeBlockedUser = this.userRepository.findById(userId).orElseThrow();
        user.getBlockedUsers().add(toBeBlockedUser);

        return this.userRepository.save(user);
    }

    @Override
    public User unblockUserById(String userId) {
        String activeUserId = this.userAuthService.getActiveUser().getId();

        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfUserIsBlocked(activeUserId,userId);

        User user = this.userRepository.findById(activeUserId).orElseThrow();
        User toBeUnblockedUser = this.userRepository.findById(userId).orElseThrow();
        user.getBlockedUsers().remove(toBeUnblockedUser);

        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Page<User> getUsers(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        return this.userRepository.findAll(PageRequest.of(page, size));
    }
}
