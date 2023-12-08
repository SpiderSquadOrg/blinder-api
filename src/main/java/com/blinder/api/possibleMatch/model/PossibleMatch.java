package com.blinder.api.possibleMatch.model;

import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "possibleMatches")
public class PossibleMatch extends BaseEntity {
    private User to;
    private User from;
    private double similarityScore;
    private PossibleMatchStatus status = PossibleMatchStatus.UNMATCHED;

}
