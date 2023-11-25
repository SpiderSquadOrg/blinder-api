package com.blinder.api.characteristics.service.impl;

import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.characteristics.repository.CharacteristicsRepository;
import com.blinder.api.characteristics.service.CharacteristicsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class CharacteristicsServiceImpl implements CharacteristicsService {
    private final CharacteristicsRepository characteristicsRepository;
    @Override
    public Characteristics addCharacteristics(Characteristics characteristics) {
        return characteristicsRepository.save(characteristics);
    }

    @Override
    public Page<Characteristics> getCharacteristics(Integer page, Integer size) {
        boolean isPageble = Objects.nonNull(page) && Objects.nonNull(size);

        if(!isPageble){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.characteristicsRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Characteristics getCharacteristicsByUserId(String userId) {
        return this.characteristicsRepository.findById(userId).orElseThrow();
    }

    @Override
    public Characteristics updateCharacteristics(String characteristicsId, Characteristics characteristics) {
        Characteristics characteristicsToUpdate = this.characteristicsRepository.findById(characteristicsId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(characteristics);

        BeanUtils.copyProperties(characteristics, characteristicsToUpdate, nullPropertyNames.toArray(new String[0]));

        this.characteristicsRepository.save(characteristicsToUpdate);
        return characteristicsToUpdate;
    }

    @Override
    public void deleteCharacteristics(String characteristicsId) {
        this.characteristicsRepository.deleteById(characteristicsId);
    }
}
