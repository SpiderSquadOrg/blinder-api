package com.blinder.api.possibleMatch.service.impl;
import com.blinder.api.Movie.model.Movie;
import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.TVSeries.model.TVSeries;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.possibleMatch.repository.PossibleMatchRepository;
import com.blinder.api.possibleMatch.service.PossibleMatchService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PossibleMatchServiceImpl implements PossibleMatchService {
    private final UserService userService;
    private final PossibleMatchRepository possibleMatchRepository;

    @Override
    public void findAndAddPotentialMatches(User currentUser, int howManyUser) {
        Characteristics userCharacteristics = currentUser.getCharacteristics();
        List<User> randomUsers = userService.getRandomUsers(howManyUser);
        List<User> filteredUsers = userService.getFilteredUsers(currentUser);

        //Intersection of randomUsers and filteredUsers
        filteredUsers.retainAll(randomUsers);

        for (User potentialMatchUser : filteredUsers) {
            if (!(currentUser.equals(potentialMatchUser)) && !(potentialMatchUser.getRole().getName().equals("admin"))) {
                double similarityScore = calculateSimilarityScore(userCharacteristics, potentialMatchUser.getCharacteristics());
                addOrUpdatePossibleMatch(currentUser, potentialMatchUser, similarityScore);
            }
        }

        List<PossibleMatch> possibleMatches = currentUser.getPossibleMatches();

        possibleMatches.sort(Comparator.comparingDouble(PossibleMatch::getSimilarityScore).reversed());

        // Keep only the top 30 matches
        if (possibleMatches.size() > 30) {
            possibleMatches.subList(30, possibleMatches.size()).clear();
        }

    }

    @Override
    public PossibleMatch likePossibleMatch(String possibleMatchId) {
        PossibleMatch possibleMatch = possibleMatchRepository.findById(possibleMatchId).orElseThrow();
        possibleMatch.setStatus(PossibleMatchStatus.LIKED);
        possibleMatchRepository.save(possibleMatch);
        return possibleMatch;
    }

    @Override
    public void dislikePossibleMatch(String possibleMatchId) {
        PossibleMatch possibleMatch = possibleMatchRepository.findById(possibleMatchId).orElseThrow();
        possibleMatch.setStatus(PossibleMatchStatus.DISLIKED);
        possibleMatchRepository.save(possibleMatch);
    }

    @Override
    public void updateMatchStatus(User user1, User user2) {
        Optional<PossibleMatch> match1 = possibleMatchRepository.findPossibleMatchByFromAndTo(user1, user2)
                .filter(match -> match.getStatus() == PossibleMatchStatus.LIKED);

        Optional<PossibleMatch> match2 = possibleMatchRepository.findPossibleMatchByFromAndTo(user2, user1)
                .filter(match -> match.getStatus() == PossibleMatchStatus.LIKED);

        if (match1.isPresent() && match2.isPresent()) {
            match1.get().setStatus(PossibleMatchStatus.MATCHED);
            match2.get().setStatus(PossibleMatchStatus.MATCHED);

            possibleMatchRepository.saveAll(Arrays.asList(match1.get(), match2.get()));

            user1.setMatched(true);
            user2.setMatched(true);
        }
    }

    @Override
    public List<PossibleMatch> getAllPossibleMatches(User currentUser) {
        List<PossibleMatch> possibleMatches = possibleMatchRepository.findAllPossibleMatchesByFrom(currentUser);

        if(possibleMatches.size() == 0){
            findAndAddPotentialMatches(currentUser, 100);
            possibleMatches = possibleMatchRepository.findAllPossibleMatchesByFrom(currentUser);
        }

        return possibleMatches;
    }

    @Override
    public List<PossibleMatch> getAllPossibleMatchesByStatus(User currentUser, PossibleMatchStatus status) {
        return possibleMatchRepository.findAllPotentialMatchesByFromAndStatus(currentUser, status);
    }

    @Override
    public List<User> getMatchedUsers(User currentUser) { return possibleMatchRepository.findMatchedUsers(currentUser); }

    @Override
    public List<User> getLikedUsers(User currentUser) { return possibleMatchRepository.findLikedUsers(currentUser);}

    @Override
    public List<User> getUsersWhoLike(User currentUser) { return possibleMatchRepository.findUsersWhoLike(currentUser);}

    @Override
    public List<User> getDislikedUsers(User currentUser) {return possibleMatchRepository.findDislikedUsers(currentUser);}

    private double calculateSimilarityScore(Characteristics characteristics1, Characteristics characteristics2) {
        List<Music> musics1 = characteristics1.getMusics();
        List<Music> musics2 = characteristics2.getMusics();
        List<MusicCategory> musicCategories1 = characteristics1.getMusicCategories();
        List<MusicCategory> musicCategories2 = characteristics2.getMusicCategories();
        List<Movie> movies1 = characteristics1.getMovies();
        List<Movie> movies2 = characteristics2.getMovies();
        List<MovieCategory> movieCategories1 = characteristics1.getMovieCategories();
        List<MovieCategory> movieCategories2 = characteristics2.getMovieCategories();
        List<TVSeries> tvSeries1 = characteristics1.getTvSeriesList();
        List<TVSeries> tvSeries2 = characteristics2.getTvSeriesList();
        List<MovieCategory> tvSeriesCategories1 = characteristics1.getTvSeriesCategories();
        List<MovieCategory> tvSeriesCategories2 = characteristics2.getTvSeriesCategories();
        List<Hobby> hobbies1 = characteristics1.getHobbies();
        List<Hobby> hobbies2 = characteristics2.getHobbies();

        double musicSimilarity = calculateSimilarity(musics1, musics2);
        double musicCategorySimilarity = calculateSimilarity(musicCategories1, musicCategories2);
        double movieSimilarity = calculateSimilarity(movies1, movies2);
        double movieCategorySimilarity = calculateSimilarity(movieCategories1, movieCategories2);
        double tvSeriesSimilarity = calculateSimilarity(tvSeries1, tvSeries2);
        double tvSeriesCategorySimilarity = calculateSimilarity(tvSeriesCategories1, tvSeriesCategories2);
        double HobbySimilarity = calculateSimilarity(hobbies1, hobbies2);

        // adjust the weights
        double musicWeight = 0.7;
        double musicCategoryWeight = 0.7;
        double movieWeight = 0.7;
        double movieCategoryWeight = 0.7;
        double tvSeriesWeight = 0.7;
        double tvSeriesCategoryWeight = 0.7;
        double hobbyWeight = 0.7;

        // the weighted average similarity score
        return (musicWeight * musicSimilarity) + (musicCategoryWeight * musicCategorySimilarity) +
                (movieWeight * movieSimilarity) + (movieCategoryWeight * movieCategorySimilarity) +
                (tvSeriesWeight * tvSeriesSimilarity) + (tvSeriesCategoryWeight * tvSeriesCategorySimilarity) +
                (hobbyWeight * HobbySimilarity);
    }

    private  <T> double calculateSimilarity(List<T> list1, List<T> list2) {
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

    private void addOrUpdatePossibleMatch(User userFrom, User userTo, double similarityScore) {

        Optional<PossibleMatch> existingMatch = possibleMatchRepository.findPossibleMatchByFromAndTo(userFrom, userTo);

        if (existingMatch.isPresent()) {
            // Update the existing match if the new score is higher or lower
            if (similarityScore != existingMatch.get().getSimilarityScore()) {
                existingMatch.get().setSimilarityScore(similarityScore);
            }
        } else {
            // Add the new match to the database
            PossibleMatch newMatch = new PossibleMatch();
            newMatch.setFrom(userFrom);
            newMatch.setTo(userTo);
            newMatch.setSimilarityScore(similarityScore);
            possibleMatchRepository.save(newMatch);
            userFrom.getPossibleMatches().add(newMatch);
        }
    }
}
