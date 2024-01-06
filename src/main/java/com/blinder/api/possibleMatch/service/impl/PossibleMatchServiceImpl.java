package com.blinder.api.possibleMatch.service.impl;
import com.blinder.api.Movie.model.Movie;
import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.Music.model.Music;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.TVSeries.model.TVSeries;
import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
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
    private final UserRepository userRepository;

    @Override
    public void findAndAddPotentialMatches(User currentUser, int howManyUser) {
        Characteristics userCharacteristics = currentUser.getCharacteristics();
        deletePossibleMatches(currentUser);

        List<User> filteredUsers = userService.getFilteredUsers(currentUser,1000, 30);

        for (User potentialMatchUser : filteredUsers) {
            if (!(currentUser.getId().equals(potentialMatchUser.getId())) &&
                    !(potentialMatchUser.getRole().getName().equals("admin"))) {
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

        // Current user can be seen in the possible match list of the user who liked:
        addOrUpdatePossibleMatch(possibleMatch.getTo(), possibleMatch.getFrom(), possibleMatch.getSimilarityScore());

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
        PossibleMatch match1 = possibleMatchRepository.findPossibleMatchByFromAndTo(user1, user2)
                .orElse(null);

        PossibleMatch match2 = possibleMatchRepository.findPossibleMatchByFromAndTo(user2, user1)
                .orElse(null);

        if (match1 != null && match1.getStatus() == PossibleMatchStatus.LIKED &&
                match2 != null && match2.getStatus() == PossibleMatchStatus.LIKED) {
            match1.setStatus(PossibleMatchStatus.MATCHED);
            match2.setStatus(PossibleMatchStatus.MATCHED);

            possibleMatchRepository.saveAll(Arrays.asList(match1, match2));

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
        }else{
            for (PossibleMatch potentialMatch : possibleMatches) {
                User potentialMatchUser = potentialMatch.getTo();
                double similarityScore = potentialMatch.getSimilarityScore();
                addOrUpdatePossibleMatch(currentUser, potentialMatchUser, similarityScore);
            }
            //TO DO: Update işi diğer özellikler için de yapılmalı (username vs) ayrı bir update eklenebilir.
        }

        return possibleMatches;
    }

    @Override
    public List<PossibleMatch> getAllPossibleMatchesByStatus(User currentUser, PossibleMatchStatus status) {
        return possibleMatchRepository.findAllPotentialMatchesByFromAndStatus(currentUser, status);
    }

    @Override
    public List<PossibleMatch> getMatchedUsers(User currentUser) { return possibleMatchRepository.findMatchedUsers(currentUser); }

    @Override
    public List<PossibleMatch> getLikedUsers(User currentUser) { return possibleMatchRepository.findLikedUsers(currentUser);}

    @Override
    public List<PossibleMatch> getUsersWhoLike(User currentUser) { return possibleMatchRepository.findUsersWhoLike(currentUser);}

    @Override
    public List<PossibleMatch> getDislikedUsers(User currentUser) {return possibleMatchRepository.findDislikedUsers(currentUser);}

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
        List<TVSeriesCategory> tvSeriesCategories1 = characteristics1.getTvSeriesCategories();
        List<TVSeriesCategory> tvSeriesCategories2 = characteristics2.getTvSeriesCategories();
        List<Hobby> hobbies1 = characteristics1.getHobbies();
        List<Hobby> hobbies2 = characteristics2.getHobbies();

        double musicSimilarity = calculateSimilarityForMusics(musics1, musics2);
        double musicCategorySimilarity = calculateSimilarityForMusicCategories(musicCategories1, musicCategories2);
        double movieSimilarity = calculateSimilarityForMovies(movies1, movies2);
        double movieCategorySimilarity = calculateSimilarityForMovieCategories(movieCategories1, movieCategories2);
        double tvSeriesSimilarity = calculateSimilarityForTVSeries(tvSeries1, tvSeries2);
        double tvSeriesCategorySimilarity = calculateSimilarityForTVSeriesCategories(tvSeriesCategories1, tvSeriesCategories2);
        double HobbySimilarity = calculateSimilarityForHobbies(hobbies1, hobbies2);

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

    private double calculateSimilarityForMusics(List<Music> list1, List<Music> list2) {
        int commonItemCount = 0;

        for (Music music : list1) {
            for (Music otherMusic : list2) {
                if (music.getSpotifyId().equals(otherMusic.getSpotifyId())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    private double calculateSimilarityForMusicCategories(List<MusicCategory> list1, List<MusicCategory> list2) {
        int commonItemCount = 0;

        for (MusicCategory musicCategory : list1) {
            for (MusicCategory otherMusicCategory : list2) {
                if (musicCategory.getName().equals(otherMusicCategory.getName())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    private double calculateSimilarityForMovies(List<Movie> list1, List<Movie> list2) {
        int commonItemCount = 0;

        for (Movie movie : list1) {
            for (Movie otherMovie : list2) {
                if (movie.getImdbId().equals(otherMovie.getImdbId())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    private double calculateSimilarityForMovieCategories(List<MovieCategory> list1, List<MovieCategory> list2) {
        int commonItemCount = 0;

        for (MovieCategory movieCategory : list1) {
            for (MovieCategory otherMovieCategory : list2) {
                if (movieCategory.getName().equals(otherMovieCategory.getName())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    private double calculateSimilarityForTVSeries(List<TVSeries> list1, List<TVSeries> list2) {
        int commonItemCount = 0;

        for (TVSeries tvSeries : list1) {
            for (TVSeries otherTVSeries : list2) {
                if (tvSeries.getImdbId().equals(otherTVSeries.getImdbId())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    private double calculateSimilarityForTVSeriesCategories(List<TVSeriesCategory> list1, List<TVSeriesCategory> list2) {
        int commonItemCount = 0;

        for (TVSeriesCategory tvSeriesCategory : list1) {
            for (TVSeriesCategory otherTVSeriesCategory : list2) {
                if (tvSeriesCategory.getName().equals(otherTVSeriesCategory.getName())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    private double calculateSimilarityForHobbies(List<Hobby> list1, List<Hobby> list2) {
        int commonItemCount = 0;

        for (Hobby hobby : list1) {
            for (Hobby otherHobby : list2) {
                if (hobby.getName().equals(otherHobby.getName())) {
                    commonItemCount++;
                    break;
                }
            }
        }

        double totalItemCount = list1.size() + list2.size();

        if (commonItemCount == 0 && totalItemCount == 0) {
            return 0;
        }

        return commonItemCount / totalItemCount;
    }

    /*private  <T> double calculateSimilarityForMusics(List<T> list1, List<T> list2) {
        Set<T> set1 = new HashSet<>(list1);
        Set<T> set2 = new HashSet<>(list2);

        int commonItemCount = 0;

        for (T item : set1) {
            if (set2.contains(item)) {
                commonItemCount++;
            }
        }

        double totalItemCount = set1.size() + set2.size();

        if((commonItemCount == 0 && totalItemCount == 0)){
            return 0;
        }

        System.out.println(commonItemCount / totalItemCount);
        return commonItemCount / totalItemCount;
    }*/

    private void addOrUpdatePossibleMatch(User userFrom, User userTo, double similarityScore) {

        PossibleMatch existingMatch = possibleMatchRepository.findPossibleMatchByFromAndTo(userFrom, userTo)
                .orElse(null);

        if (existingMatch != null) {
            if(existingMatch.getStatus() == PossibleMatchStatus.UNMATCHED){
                // Update the existing match if the new score is higher or lower
                if (similarityScore != existingMatch.getSimilarityScore()) {
                    existingMatch.setSimilarityScore(similarityScore);
                }
            }else{
                deletePossibleMatch(userFrom, userTo);
            }

        } else {
            PossibleMatch newMatch = new PossibleMatch();
            newMatch.setFrom(userFrom);
            newMatch.setTo(userTo);
            newMatch.setSimilarityScore(similarityScore);
            possibleMatchRepository.save(newMatch);

            List<PossibleMatch> possibleMatches = userFrom.getPossibleMatches();
            possibleMatches.add(newMatch);
            userFrom.setPossibleMatches(possibleMatches);
            userRepository.save(userFrom);
        }
    }

    private void deletePossibleMatch(User userFrom,User userTo)
    {
        PossibleMatch possibleMatch = possibleMatchRepository.findPossibleMatchByFromAndTo(userFrom, userTo).orElseThrow();
        possibleMatchRepository.delete(possibleMatch);

        userFrom.setPossibleMatches(userFrom.getPossibleMatches().stream().filter((match)->
                (!Objects.equals(possibleMatch.getTo().getId(), userTo.getId()))).toList());

        userRepository.save(userFrom);
    }

    private void deletePossibleMatches(User userFrom)
    {
        List<PossibleMatch> possibleMatches = possibleMatchRepository.findAllPossibleMatchesByFrom(userFrom);
        possibleMatchRepository.deleteAll(possibleMatches);

        userFrom.setPossibleMatches(new ArrayList<>());

        userRepository.save(userFrom);
    }

}
