package com.blinder.api.user.repository;

import com.blinder.api.filter.model.LocationType;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<User> findByUsername(String username);

    @Query("SELECT u.images FROM User u WHERE u.id = :id")
    List<String> getImagesById(@Param("id") String id);

    @Query("SELECT u FROM User u WHERE EXTRACT(YEAR FROM u.birthDate) BETWEEN :lowerBound AND :upperBound "
            //"AND (u.filter.genders IS EMPTY OR SIZE(u.filter.genders) = 0 OR :gender MEMBER OF u.filter.genders) " +
            //"AND (:countryIso2 IS NULL OR u.location.countryIso2 = :countryIso2) " +
            //"AND (:stateIso2 IS NULL OR u.location.stateIso2 = :stateIso2)"
            )
    List<User> findFilteredUsersWithQuery(
            @Param("lowerBound") int lowerBound,
            @Param("upperBound") int upperBound
            //@Param("gender") Set<Gender> gender,
            //@Param("countryIso2") String countryIso2,
            //@Param("stateIso2") String stateIso2
    );



}
