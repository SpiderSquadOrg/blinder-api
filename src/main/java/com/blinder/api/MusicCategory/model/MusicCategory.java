package com.blinder.api.MusicCategory.model;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.model.BaseEntity;
import jakarta.persistence.*;
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;

}