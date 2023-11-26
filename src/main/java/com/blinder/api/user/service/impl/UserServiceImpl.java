package com.blinder.api.user.service.impl;

import com.blinder.api.location.model.Location;
import com.blinder.api.location.service.LocationService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.rules.UserBusinessRules;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final UserAuthService userAuthService;
    private final UserBusinessRules userBusinessRules;
    @Override
    public User getUserById(String id) {
        this.userBusinessRules.checkIfUserExists(id);
        return this.userRepository.findById(id).orElseThrow();
    }

    @Override
    public User addUser(User user) {
        Location newLocation = this.locationService.addLocation(user.getLocation());
        User newUser = this.userRepository.save(user);
        newLocation.setUser(newUser);
        this.locationService.updateLocation(newLocation.getId(),newLocation);
        return newUser;
    }

    @Override
    public User updateUserById(String userId, User user) {
        this.userBusinessRules.checkIfUserExists(userId);
        boolean isLocationUpdated = (user.getLocation()!=null) && (
                (user.getLocation().getCity()!=null) || (user.getLocation().getRegion()!=null) || (user.getLocation().getCountry()!=null)
                );

        User userToUpdate = this.userRepository.findById(userId).orElseThrow();

        if (isLocationUpdated) {
            Location newLocation = this.locationService.updateLocation(userToUpdate.getLocation().getId(),user.getLocation());
            user.setLocation(newLocation);
        }

        Set<String> nullPropertyNames = getNullPropertyNames(user);

        BeanUtils.copyProperties(user, userToUpdate, nullPropertyNames.toArray(new String[0]));

        this.userRepository.save(userToUpdate);
        return userToUpdate;
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
