package com.blinder.api.possibleMatch.model;

import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "possible_matches")
public class PossibleMatch extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user_id", nullable = false)
    @JsonBackReference
    private User from;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user_id", nullable = false)
    @JsonBackReference
    private User to;

    private double similarityScore;

    @Enumerated(EnumType.STRING)
    private PossibleMatchStatus status = PossibleMatchStatus.UNMATCHED;
}
