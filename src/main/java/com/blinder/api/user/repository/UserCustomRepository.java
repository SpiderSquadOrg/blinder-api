package com.blinder.api.user.repository;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.filter.model.LocationType;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserCustomRepository {
    Page<User> searchUsersByFilter(String email, String name, String surname, String username, String[] roleNames, String[] genderNames, String ageLowerBound, String ageUpperBound, String region, String country, String city, Boolean isMatched, Boolean isBanned, Pageable pageable, SortCriteria sortCriteria);
    List<User> findFilteredUsers(
            Set<Gender> genders, int ageLowerBound, int ageUpperBound,
            String countryIso2, String stateIso2);
}
