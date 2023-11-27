package com.blinder.api.user.repository;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserCustomRepository {
    Page<User> searchUsersByFilter(String email, String name, String surname, String username, String[] roleNames, String[] genderNames, String ageLowerBound, String ageUpperBound, String region, String country, String city, Boolean isMatched, Boolean isBanned, Pageable pageable, SortCriteria sortCriteria);
}
