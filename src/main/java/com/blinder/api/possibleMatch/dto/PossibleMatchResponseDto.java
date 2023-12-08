package com.blinder.api.possibleMatch.dto;

import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PossibleMatchResponseDto {
    private String id;
    private User to;
    private User from;
    private double similarityScore;
    private PossibleMatchStatus status;
}
