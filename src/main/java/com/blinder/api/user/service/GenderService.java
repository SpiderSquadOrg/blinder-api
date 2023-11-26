package com.blinder.api.user.service;

import com.blinder.api.user.model.Gender;
import org.springframework.data.domain.Page;

public interface GenderService {
    Gender addGender(Gender gender);

    Page<Gender> getGenders(Integer page, Integer size);

    Gender getGenderById(String genderId);

    Gender updateGender(String genderId, Gender gender);

    void deleteGender(String genderId);
}
