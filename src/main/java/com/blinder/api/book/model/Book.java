package com.blinder.api.book.model;

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
public class Book extends BaseEntity {
    private String name;
    private String author;
}
