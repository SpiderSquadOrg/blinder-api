package com.blinder.api.filter.mapper;

import com.blinder.api.filter.model.Filter;
import com.blinder.api.user.dto.GenderRequestDto;
import com.blinder.api.user.model.Gender;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import com.blinder.api.filter.dto.FilterResponseDto;
import com.blinder.api.filter.dto.UpdateFilterRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FilterMapper {
    FilterMapper INSTANCE = Mappers.getMapper(FilterMapper.class);

    FilterResponseDto filterToFilterResponseDto(Filter filter);

    @Mapping(target = "genders", source = "genders")
    Filter updateFilterRequestDtoToFilter(UpdateFilterRequestDto updateFilterRequestDto);

    default Set<Gender> mapGenderRequestDtoSetToGenderSet(Set<GenderRequestDto> genderRequestDtos) {
        return genderRequestDtos.stream()
                .map(this::genderRequestDtoToGender)
                .collect(Collectors.toSet());
    }

    Gender genderRequestDtoToGender(GenderRequestDto genderRequestDto);
}
