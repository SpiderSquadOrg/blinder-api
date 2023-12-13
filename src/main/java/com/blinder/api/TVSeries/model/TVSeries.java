package com.blinder.api.TVSeries.model;

import com.blinder.api.model.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class TVSeries extends BaseEntity {
    private String imdbId;
    private String name;
    private int year;
    private String image;
}
