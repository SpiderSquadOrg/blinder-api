package com.blinder.api.user.model;

import com.blinder.api.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Gender extends BaseEntity {
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "gender")
    private List<User> users = new ArrayList<>();

    public Gender(String name) {
        this.name = name;
    }
}
