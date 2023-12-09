package com.blinder.api.MovieCategory.model;

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
public class MovieCategory extends BaseEntity {
    private String name;

}