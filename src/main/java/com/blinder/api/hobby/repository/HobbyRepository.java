package com.blinder.api.hobby.repository;

import com.blinder.api.hobby.model.Hobby;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HobbyRepository extends JpaRepository<Hobby, String>{
    boolean existsByName(String name);

}
