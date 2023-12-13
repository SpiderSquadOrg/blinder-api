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

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
public class Music extends BaseEntity {
    private String spotifyId;
    private String name;
    private List<String> artists;
    private String album;
    private String image;
}
