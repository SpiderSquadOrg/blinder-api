package com.blinder.api.possibleMatch.service.impl;

import com.blinder.api.possibleMatch.model.PossibleMatch;
import com.blinder.api.possibleMatch.repository.PossibleMatchRepository;
import com.blinder.api.possibleMatch.service.PossibleMatchManagementService;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserRepository;
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

    public void deletePossibleMatches(User userFrom)
    {
        List<PossibleMatch> possibleMatches = possibleMatchRepository.findAllPossibleMatchesByFrom(userFrom);
        possibleMatchRepository.deleteAll(possibleMatches);

        userFrom.setPossibleMatches(new ArrayList<>());

        userRepository.save(userFrom);

        List<PossibleMatch> pos = possibleMatchRepository.findAllPossibleMatchesByFrom(userFrom);
        if(pos != null){
            System.out.println("bla");
        }


    }

    public void deletePossibleMatch(User userFrom,User userTo)
    {
        PossibleMatch possibleMatch = possibleMatchRepository.findPossibleMatchByFromAndTo(userFrom, userTo).orElseThrow();
        possibleMatchRepository.delete(possibleMatch);

        userFrom.setPossibleMatches(userFrom.getPossibleMatches().stream().filter((match)->
                (!Objects.equals(possibleMatch.getTo().getId(), userTo.getId()))).toList());

        userRepository.save(userFrom);
    }

}
