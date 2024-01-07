package com.blinder.api.possibleMatch.service;

import com.blinder.api.user.model.User;

public interface PossibleMatchManagementService {

    void deletePossibleMatches(User userFrom);
    void deletePossibleMatch(User userFrom,User userTo);
}
