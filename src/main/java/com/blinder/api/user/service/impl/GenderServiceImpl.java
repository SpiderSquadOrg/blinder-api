package com.blinder.api.user.service.impl;

import com.blinder.api.user.model.Gender;
import com.blinder.api.user.repository.GenderRepository;
import com.blinder.api.user.rules.GenderBusinessRules;
import com.blinder.api.user.service.GenderService;
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
public class GenderServiceImpl implements GenderService {
    private final GenderRepository genderRepository;
    private final GenderBusinessRules genderBusinessRules;

    @Override
    public Gender addGender(Gender gender) {
        this.genderBusinessRules.checkIfNameExists(gender.getName());
        return this.genderRepository.save(gender);
    }

    @Override
    public Page<Gender> getGenders(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }

        return this.genderRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Gender getGenderById(String genderId) {
        return this.genderRepository.findById(genderId).orElseThrow();
    }

    @Override
    public Gender updateGender(String genderId, Gender gender) {
        Gender genderToUpdate = this.genderRepository.findById(genderId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(gender);

        BeanUtils.copyProperties(gender, genderToUpdate, nullPropertyNames.toArray(new String[0]));

        this.genderRepository.save(genderToUpdate);
        return genderToUpdate;
    }

    @Override
    public void deleteGender(String genderId) {
        this.genderRepository.deleteById(genderId);
    }
}
