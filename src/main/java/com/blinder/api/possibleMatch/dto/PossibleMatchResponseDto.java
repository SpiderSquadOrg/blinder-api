package com.blinder.api.possibleMatch.dto;

import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PossibleMatchResponseDto {
    private String id;
    //private String to;
    //private String from;
    private UserResponseDto to;
    private UserResponseDto from;
    private double similarityScore;
    private PossibleMatchStatus status;
}
