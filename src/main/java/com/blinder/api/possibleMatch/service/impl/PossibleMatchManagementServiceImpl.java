package com.blinder.api.possibleMatch.service.impl;

import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.possibleMatch.repository.PossibleMatchRepository;
import com.blinder.api.possibleMatch.service.PossibleMatchManagementService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PossibleMatchManagementServiceImpl implements PossibleMatchManagementService {
    private final UserRepository userRepository;
    private final PossibleMatchRepository possibleMatchRepository;


    @Transactional
    public void deletePossibleMatches(User userFrom) {
        List<PossibleMatch> possibleMatches = possibleMatchRepository.findAllPotentialMatchesByFromAndStatus(userFrom, PossibleMatchStatus.UNMATCHED);

        if (!possibleMatches.isEmpty()) {
            for (PossibleMatch possibleMatch : possibleMatches) {
                possibleMatch.setTo(null);
                possibleMatch.setFrom(null);
            }

            // Değişiklikleri kaydet
            possibleMatchRepository.saveAll(possibleMatches);

            // Kullanıcıdan possibleMatches koleksiyonunu kaldır
            userFrom.getPossibleMatches().removeAll(possibleMatches);

            // Kullanıcıyı güncelle
            User updatedUser = userRepository.save(userFrom);

            // PossibleMatch nesnelerini sil
            possibleMatchRepository.deleteAll(possibleMatches);

            System.out.println("Matches deleted successfully.");
        } else {
            System.out.println("No matches found to delete.");
        }

        List<PossibleMatch> pos = possibleMatchRepository.findAllPossibleMatchesByFrom(userFrom);
        if (pos != null) {
            System.out.println("bla");
        }
    }

    /*
    public void deletePossibleMatches(User userFrom)
    {

        //userFrom.setPossibleMatches(new ArrayList<>());
        //userRepository.save(userFrom);

        List<PossibleMatch> possibleMatches = possibleMatchRepository.findAllPotentialMatchesByFromAndStatus(userFrom,
                PossibleMatchStatus.UNMATCHED);

        if (possibleMatches.isEmpty()) {
            System.out.println("No matches found to delete.");
        } else {
            possibleMatchRepository.deleteAll(possibleMatches);
            System.out.println("Matches deleted successfully.");
        }

        List<PossibleMatch> pos = possibleMatchRepository.findAllPossibleMatchesByFrom(userFrom);
        if(pos != null){
            System.out.println("bla");
        }

    }*/

    public void deletePossibleMatch(User userFrom,User userTo)
    {
        PossibleMatch possibleMatch = possibleMatchRepository.findPossibleMatchByFromAndTo(userFrom, userTo).orElseThrow();
        possibleMatchRepository.delete(possibleMatch);

        userFrom.setPossibleMatches(userFrom.getPossibleMatches().stream().filter((match)->
                (!Objects.equals(possibleMatch.getTo().getId(), userTo.getId()))).toList());

        userRepository.save(userFrom);
    }

}
