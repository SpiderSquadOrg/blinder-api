package com.blinder.api.hobby.service;

import com.blinder.api.hobby.dto.UpdateHobbyRequestDto;
import com.blinder.api.hobby.model.Hobby;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;


public interface HobbyService {
    public Page<Hobby> getHobbies(Integer page, Integer size);
    public Hobby addHobby(Hobby hobby);
    public Hobby updateHobby(String hobbyId, UpdateHobbyRequestDto updateHobbyRequestDto);

    public void deleteHobby(String hobbyId);
    public Hobby getHobbyById(String hobbyId);
}
