package com.blinder.api.characteristics.model;

import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
public class Characteristics extends BaseEntity {
    @OneToMany
    private List<MusicCategory> musicCategories;
    @OneToOne
    private User user;

}
