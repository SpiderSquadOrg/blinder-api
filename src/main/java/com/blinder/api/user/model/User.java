package com.blinder.api.user.model;

import com.blinder.api.location.model.Location;
import com.blinder.api.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "users")
public class User extends BaseEntity {
    private String name;
    private String surname;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    private Gender gender;

    private Date birthDate;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images = new ArrayList<>();

  //  private Filter filter;

    @OneToMany(fetch = FetchType.LAZY)
    private List<User> blockedUsers = new ArrayList<>();

   // private List<PossibleMatches> possibleMatches = new ArrayList<>();

   // private Characteristics characteristics;

    private boolean isMatched;
    private boolean isBanned;

    @Override
    public void onPrePersist() {
        super.onPrePersist();
        this.isMatched = false;
        this.isBanned = false;
    }

    public int getAge() {
        Date now = new Date();
        long diff = now.getTime() - birthDate.getTime();
        return (int) (diff / (1000L * 60 * 60 * 24 * 365));
    }
}

