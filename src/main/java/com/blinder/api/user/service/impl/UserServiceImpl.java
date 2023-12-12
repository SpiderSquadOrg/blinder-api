package com.blinder.api.user.service.impl;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.common.sort.SortDirection;
import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.model.LocationType;
import com.blinder.api.filter.service.FilterService;
import com.blinder.api.location.service.LocationService;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.Role;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserCustomRepository;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.rules.UserBusinessRules;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.service.RoleService;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final LocationService locationService;
    private final UserAuthService userAuthService;
    private final UserBusinessRules userBusinessRules;
    private final FilterService filterService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(String id) {
        this.userBusinessRules.checkIfUserExists(id);
        return this.userRepository.findById(id).orElseThrow();
    }

    @Override
    public User addUser(User user) {
        this.userBusinessRules.checkIfEmailExists(user.getEmail());
        this.userBusinessRules.checkIfUsernameExists(user.getUsername());
        this.userBusinessRules.checkIfGenderDoesNotExists(user.getGender().getId());
        this.userBusinessRules.checkIfRoleDoesNotExists(user.getRole().getId());
        this.userBusinessRules.checkIfPhoneNumberExists(user.getPhoneNumber());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = this.userRepository.save(user);

        this.filterService.createDefaultFilterForUser(newUser.getId());

        return newUser;
    }

    @Override
    public User register(User user) {
        Role normalUserRole = this.roleService.getRoleByName("normal");
        user.setRole(normalUserRole);
        return this.addUser(user);
    }

    @Override
    public User updateUserById(String userId, User user) {
        this.userBusinessRules.checkIfUserExists(userId);
        this.userBusinessRules.checkIfEmailExists(user.getEmail());
        this.userBusinessRules.checkIfUsernameExists(user.getUsername());
        this.userBusinessRules.checkIfGenderDoesNotExists(user.getGender().getId());
        this.userBusinessRules.checkIfRoleDoesNotExists(user.getRole().getId());


        User userToUpdate = this.userRepository.findById(userId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(user);

        BeanUtils.copyProperties(user, userToUpdate, nullPropertyNames.toArray(new String[0]));

        this.userRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Override
    public Page<User> searchUsers(Integer page, Integer size, String email, String name, String surname, String username, String[] roleNames, String[] genderNames, String ageLowerBound, String ageUpperBound, String region, String country, String city, Boolean isMatched, Boolean isBanned, String sortBy, String sortDirection) {
        if(Objects.isNull(page) || Objects.isNull(size)){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.isNull(sortBy) || sortBy.isEmpty()) {
            sortBy = "createdDate";
        }

        SortDirection so;
        if (Objects.nonNull(sortDirection) && sortDirection.equals("ASCENDING")) {
            so = SortDirection.ASCENDING;
        } else {
            so = SortDirection.DESCENDING;
        }

        SortCriteria sortCriteria = new SortCriteria(sortBy, so);
        return this.userCustomRepository.searchUsersByFilter(email, name, surname, username, roleNames, genderNames, ageLowerBound, ageUpperBound, region, country, city, isMatched, isBanned, pageable, sortCriteria);
    }

    @Override
    public void deleteUserById(String userId) {
        this.userBusinessRules.checkIfUserExists(userId);

        User userToDelete = this.userRepository.findById(userId).orElseThrow();
        userToDelete.setLocation(null);

        this.userRepository.save(userToDelete);
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

    @Override
    public List<User> getFilteredUsers(User user) {
        Filter userFilter = user.getFilter();

        Set<Gender> allowedGenders = userFilter.getGenders();
        int ageLowerBound = userFilter.getAgeLowerBound();
        int ageUpperBound = userFilter.getAgeUpperBound();
        LocationType locationType = userFilter.getLocationType();
        String locationName = userFilter.getLocationId();

        return userCustomRepository.findFilteredUsers(allowedGenders, ageLowerBound, ageUpperBound, locationType, locationName);
    }

    @Override
    public List<User> getRandomUsers(int howManyUser) {
        List<User> allUsers = userRepository.findAll();
        Collections.shuffle(allUsers);

        return allUsers.subList(0, Math.min(howManyUser, allUsers.size()));
    }
}
