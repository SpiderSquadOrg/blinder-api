package com.blinder.api.filter.model;
import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "filters")
public class Filter extends BaseEntity {
    private static final int DEFAULT_AGE_LOWER_BOUND = 18;
    private static final int DEFAULT_AGE_UPPER_BOUND = 99;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Gender> genders;

    //@OneToOne(mappedBy = "filter", cascade = CascadeType.ALL)
    //private LocationFilter locationFilter;

    private int ageLowerBound = DEFAULT_AGE_LOWER_BOUND;
    private int ageUpperBound = DEFAULT_AGE_UPPER_BOUND;

    public Filter(User user, Set<Gender> allGenders) {
        this.user = user;
        this.genders = allGenders;
    }

    public static int getDefaultAgeLowerBound() {
        return DEFAULT_AGE_LOWER_BOUND;
    }

    public static int getDefaultAgeUpperBound() {
        return DEFAULT_AGE_UPPER_BOUND;
    }
}