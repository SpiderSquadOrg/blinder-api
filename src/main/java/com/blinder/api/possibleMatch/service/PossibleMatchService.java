package com.blinder.api.possibleMatch.service;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.user.model.User;
import java.util.List;

public interface PossibleMatchService {

    void findAndAddPotentialMatches(User user, int howManyUser);
    void addOrUpdatePossibleMatch(User user1, User user2, double similarityScore);

    double calculateSimilarityScore(Characteristics characteristics1, Characteristics characteristics2);
    <T> double calculateSimilarity(List<T> list1, List<T> list2);

    void likePossibleMatch(User currentUser, PossibleMatch possibleMatch);
    void dislikePossibleMatch(PossibleMatch possibleMatch);
    void updateMatchStatus(User user1, User user2);

    List<PossibleMatch> getAllPossibleMatches(User currentUser);
    List<PossibleMatch> getAllPossibleMatchesByStatus(User currentUser, PossibleMatchStatus status);

    List<User> getMatchedUsers(User currentUser);
    List<User> getLikedUsers(User currentUser);
    List<User> getDislikedUsers(User currentUser);

}
