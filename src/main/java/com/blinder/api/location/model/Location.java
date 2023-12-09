package com.blinder.api.location.model;

import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Location extends BaseEntity {
    @OneToOne
    private User user;
    private String countryName;
    private String countryId;
    private String countryIso2;
    private String state;
}
