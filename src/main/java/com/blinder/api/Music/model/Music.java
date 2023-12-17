package com.blinder.api.Music.model;

import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.model.BaseEntity;
import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String spotifyId;
    private String name;

    private String artist;
    private String album;
    private String image;
}
