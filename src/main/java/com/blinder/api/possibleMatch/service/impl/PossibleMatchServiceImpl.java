package com.blinder.api.possibleMatch.service.impl;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.possibleMatch.repository.PossibleMatchRepository;
import com.blinder.api.possibleMatch.service.PossibleMatchService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.service.UserService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PossibleMatchServiceImpl implements PossibleMatchService {
    private final UserService userService;
    private final PossibleMatchRepository possibleMatchRepository;
    private final List<PossibleMatch> possibleMatches;

    public PossibleMatchServiceImpl(UserService userService, PossibleMatchRepository possibleMatchRepository) {
        this.userService = userService;
        this.possibleMatchRepository = possibleMatchRepository;
        this.possibleMatches = new ArrayList<>();
    }

    @Override
    public void findAndAddPotentialMatches(User user, int howManyUser) {
        Characteristics userCharacteristics = user.getCharacteristics();
        List<User> randomUsers = userService.getRandomUsers(howManyUser);
        List<User> filteredUsers = userService.getFilteredUsers(user);

        // Intersection of randomUsers and filteredUsers
        filteredUsers.retainAll(randomUsers);

        for (User potentialMatchUser : filteredUsers) {
            if (!user.equals(potentialMatchUser)) {
                double similarityScore = calculateSimilarityScore(userCharacteristics, potentialMatchUser.getCharacteristics());
                addOrUpdatePossibleMatch(user, potentialMatchUser, similarityScore);
            }
        }

        possibleMatches.sort(Comparator.comparingDouble(PossibleMatch::getSimilarityScore).reversed());

        // Keep only the top 30 matches
        if (possibleMatches.size() > 30) {
            possibleMatches.subList(30, possibleMatches.size()).clear();
        }
    }

    @Override
    public void addOrUpdatePossibleMatch(User user1, User user2, double similarityScore) {

        Optional<PossibleMatch> existingMatch = possibleMatchRepository.findFromPossibleMatches(user1, user2);

        if (existingMatch.isPresent()) {
            // Update the existing match if the new score is higher
            if (similarityScore > existingMatch.get().getSimilarityScore()) {
                existingMatch.get().setSimilarityScore(similarityScore);
            }
        } else {
            // Add the new match to the database
            PossibleMatch newMatch = new PossibleMatch();
            newMatch.setTo(user1);
            newMatch.setFrom(user2);
            newMatch.setSimilarityScore(similarityScore);
            possibleMatchRepository.save(newMatch);
        }
    }

    @Override
    public double calculateSimilarityScore(Characteristics characteristics1, Characteristics characteristics2) {
        List<Music> musics1 = characteristics1.getMusics();
        List<Music> musics2 = characteristics2.getMusics();
        List<MusicCategory> musicCategories1 = characteristics1.getMusicCategories();
        List<MusicCategory> musicCategories2 = characteristics2.getMusicCategories();

        double musicSimilarity = calculateSimilarity(musics1, musics2);
        double categorySimilarity = calculateSimilarity(musicCategories1, musicCategories2);

        // You may adjust the weights for different characteristics based on importance
        double musicWeight = 0.7;
        double categoryWeight = 0.3;

        // Calculate the weighted average similarity score
        return (musicWeight * musicSimilarity) + (categoryWeight * categorySimilarity);
    }

    @Override
    public <T> double calculateSimilarity(List<T> list1, List<T> list2) {
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);

        int commonItemCount = 0;

        for (T item : set1) {
            if (set2.contains(item)) {
                commonItemCount++;
            }
        }

        double totalItemCount = set1.size() + set2.size();
        return commonItemCount / totalItemCount;
    }

    @Override
    public void likePossibleMatch(User currentUser, PossibleMatch possibleMatch) {
        possibleMatch.setStatus(PossibleMatchStatus.LIKED);
        possibleMatchRepository.save(possibleMatch);

        updateMatchStatus(currentUser, possibleMatch.getFrom());
    }

    @Override
    public void dislikePossibleMatch(PossibleMatch possibleMatch) {
        possibleMatch.setStatus(PossibleMatchStatus.DISLIKED);
        possibleMatchRepository.save(possibleMatch);
    }

    @Override
    public void updateMatchStatus(User user1, User user2) {
        Optional<PossibleMatch> match1 = possibleMatchRepository.findFromPossibleMatches(user1, user2)
                .filter(match -> match.getStatus() == PossibleMatchStatus.LIKED);

        Optional<PossibleMatch> match2 = possibleMatchRepository.findFromPossibleMatches(user2, user1)
                .filter(match -> match.getStatus() == PossibleMatchStatus.LIKED);

        if (match1.isPresent() && match2.isPresent()) {
            match1.get().setStatus(PossibleMatchStatus.MATCHED);
            match2.get().setStatus(PossibleMatchStatus.MATCHED);

            possibleMatchRepository.saveAll(Arrays.asList(match1.get(), match2.get()));
        }
    }

    @Override
    public List<PossibleMatch> getAllPossibleMatches(User currentUser) {
        List<PossibleMatch> usersPossibleMatches = possibleMatchRepository.findAllPossibleMatches(currentUser);

        if(usersPossibleMatches.size() == 0){
            findAndAddPotentialMatches(currentUser, 100);
            usersPossibleMatches = possibleMatchRepository.findAllPossibleMatches(currentUser);
        }

        return usersPossibleMatches;
    }

    @Override
    public List<PossibleMatch> getAllPossibleMatchesByStatus(User currentUser, PossibleMatchStatus status) {
        return possibleMatchRepository.findAllPossibleMatchesByStatus(currentUser, status);
    }

    @Override
    public List<User> getMatchedUsers(User currentUser) {
        return possibleMatchRepository.findMatchedUsers(currentUser);
    }

    @Override
    public List<User> getLikedUsers(User currentUser) {
        return possibleMatchRepository.findLikedUsers(currentUser);
    }

    @Override
    public List<User> getDislikedUsers(User currentUser) {
        return possibleMatchRepository.findDislikedUsers(currentUser);
    }
}
