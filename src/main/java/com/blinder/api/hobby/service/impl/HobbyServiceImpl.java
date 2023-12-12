package com.blinder.api.hobby.service.impl;

import com.blinder.api.hobby.dto.UpdateHobbyRequestDto;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.hobby.repository.HobbyRepository;
import com.blinder.api.hobby.service.HobbyService;
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
public class HobbyServiceImpl implements HobbyService {
    private final HobbyRepository hobbyRepository;
    @Override
    public Hobby addHobby(Hobby hobby) {
        return hobbyRepository.save(hobby);
    }

    @Override
    public Page<Hobby> getHobbies(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.hobbyRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Hobby getHobbyById(String hobbyId) {
        return this.hobbyRepository.findById(hobbyId).orElseThrow();
    }

    @Override
    public Hobby updateHobby(String hobbyId, UpdateHobbyRequestDto updateHobbyRequestDto) {
        Hobby hobbyToUpdate = this.hobbyRepository.findById(hobbyId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(updateHobbyRequestDto);

        BeanUtils.copyProperties(updateHobbyRequestDto, hobbyToUpdate, nullPropertyNames.toArray(new String[0]));

        this.hobbyRepository.save(hobbyToUpdate);
        return hobbyToUpdate;
    }

    @Override
    public void deleteHobby(String hobbyId) {
        this.hobbyRepository.deleteById(hobbyId);
    }


}
