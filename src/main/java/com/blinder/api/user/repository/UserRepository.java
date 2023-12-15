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
}
