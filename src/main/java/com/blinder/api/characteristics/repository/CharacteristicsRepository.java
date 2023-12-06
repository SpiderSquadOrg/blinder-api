package com.blinder.api.characteristics.repository;

import com.blinder.api.characteristics.model.Characteristics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacteristicsRepository extends JpaRepository<Characteristics, String> {
    Optional<Characteristics> findCharacteristicsByUserId(String userId);
}
