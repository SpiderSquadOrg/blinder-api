package com.blinder.api.hobby.repository;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.hobby.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HobbyRepository extends JpaRepository<Hobby, String>{
    boolean existsByName(String name);
    Optional<Hobby> findHobbyByName(String name);

    void deleteByName(String hobbyName);
}
