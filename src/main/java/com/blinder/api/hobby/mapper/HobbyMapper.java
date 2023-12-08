package com.blinder.api.hobby.mapper;

import com.blinder.api.hobby.dto.HobbyResponseDto;
import com.blinder.api.hobby.dto.UpdateHobbyRequestDto;
import com.blinder.api.hobby.model.Hobby;
import com.blinder.api.hobby.dto.CreateHobbyRequestDto;
import com.blinder.api.hobby.dto.HobbyResponseDto;
import com.blinder.api.hobby.dto.UpdateHobbyRequestDto;
import com.blinder.api.hobby.mapper.HobbyMapper;
import com.blinder.api.hobby.model.Hobby;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HobbyMapper {

    HobbyMapper INSTANCE = Mappers.getMapper(HobbyMapper.class);


    List<HobbyResponseDto> hobbyToHobbyResponseDto(List<Hobby> hobbies);


    HobbyResponseDto hobbyToHobbyResponseDto(Hobby hobby);

    Hobby createHobbyRequestDtoToHobby(CreateHobbyRequestDto createHobbyRequestDto);


    UpdateHobbyRequestDto updateHobbyRequestDtoToHobby(UpdateHobbyRequestDto updateHobbyRequestDto);


    default Page<HobbyResponseDto> hobbyToHobbyResponseDto(Page<Hobby> hobbyPage) {
        List<HobbyResponseDto> responseDtoList = hobbyToHobbyResponseDto(hobbyPage.getContent());
        return new PageImpl<>(responseDtoList, hobbyPage.getPageable(), hobbyPage.getTotalElements());
    }
}
