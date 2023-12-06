package com.blinder.api.MusicCategory.model;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class MusicCategory extends BaseEntity {
    private String name;

}