package com.blinder.api.possibleMatch.service;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.possibleMatch.dto.PossibleMatchResponseDto;
import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.user.model.User;
import java.util.List;

public interface PossibleMatchService {

    void findAndAddPotentialMatches(User user, int howManyUser);

    PossibleMatch likePossibleMatch(String possibleMatchId);
    void dislikePossibleMatch(String possibleMatchId);
    void updateMatchStatus(User user1, User user2);

    List<PossibleMatch> getAllPossibleMatches(User currentUser);
    List<PossibleMatch> getAllPossibleMatchesByStatus(User currentUser, PossibleMatchStatus status);

    List<User> getMatchedUsers(User currentUser);
    List<User> getLikedUsers(User currentUser);
    List<User> getUsersWhoLike(User currentUser);
    List<User> getDislikedUsers(User currentUser);

}
