package com.blinder.api.report.model;

import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class Report extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    private User reporter;
    @ManyToOne(fetch = FetchType.EAGER)
    private User reported;
    private String description;
    private String reason;
}
