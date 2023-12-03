package com.blinder.api.user.mapper;

import com.blinder.api.user.dto.GenderRequestDto;
import com.blinder.api.user.dto.GenderResponseDto;
import com.blinder.api.user.model.Gender;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;


@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface GenderMapper {
    GenderMapper INSTANCE = Mappers.getMapper(GenderMapper.class);

    List<GenderResponseDto> genderToGenderResponseDto(List<Gender> genders);

    GenderResponseDto genderToGenderResponseDto(Gender gender);
    Gender genderResponseDtoToGender(GenderResponseDto genderResponseDto);

    Gender genderRequestDtoToGender(GenderRequestDto genderRequestDto);

    //Set<Gender> genderRequestDtoListToGenderSet(List<GenderRequestDto> genderRequestDtos);

    default Page<GenderResponseDto> genderToGenderResponseDto(Page<Gender> genderPage) {
        List<GenderResponseDto> responseDtoList = genderToGenderResponseDto(genderPage.getContent());
        return new PageImpl<>(responseDtoList, genderPage.getPageable(), genderPage.getTotalElements());
    }
}