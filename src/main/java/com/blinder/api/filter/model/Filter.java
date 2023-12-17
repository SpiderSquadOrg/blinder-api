package com.blinder.api.filter.model;
import com.blinder.api.model.BaseEntity;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private static final int DEFAULT_AGE_LOWER_BOUND = 18;
    private static final int DEFAULT_AGE_UPPER_BOUND = 99;
    private static final String DEFAULT_LOCATION = "";

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Gender> genders;

    private int ageLowerBound = DEFAULT_AGE_LOWER_BOUND;
    private int ageUpperBound = DEFAULT_AGE_UPPER_BOUND;
    private String countryIso2 = DEFAULT_LOCATION;
    private String stateIso2 = DEFAULT_LOCATION;

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

    public static String getDefaultCountryIso2(){ return DEFAULT_LOCATION; }

    public static String getDefaultStateIso2(){ return DEFAULT_LOCATION; }
}