package com.blinder.api.Movie.model;

import com.blinder.api.MovieCategory.model.MovieCategory;
import com.blinder.api.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class Movie extends BaseEntity {

    private String moviesDatabaseId;

}