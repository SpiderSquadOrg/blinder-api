package com.blinder.api.characteristics.service;

import com.blinder.api.characteristics.model.Characteristics;
import org.springframework.data.domain.Page;

public interface CharacteristicsService {
    Characteristics addCharacteristics(Characteristics characteristics);

    Page<Characteristics> getCharacteristics(Integer page, Integer size);

    Characteristics getCharacteristicsByUserId(String userId);

    Characteristics updateCharacteristics(String characteristicsId, Characteristics characteristics);

    void deleteCharacteristics(String characteristicsId);
}
