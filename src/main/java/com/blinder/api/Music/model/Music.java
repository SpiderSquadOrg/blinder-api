package com.blinder.api.Music.model;

import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class Music extends BaseEntity {

    private String name;

    @ManyToOne
    private MusicCategory musicCategory;

}
