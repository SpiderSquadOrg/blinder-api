package com.blinder.api.filter.mapper;

import com.blinder.api.filter.model.Filter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import com.blinder.api.filter.dto.CreateFilterRequestDto;
import com.blinder.api.filter.dto.FilterResponseDto;
import com.blinder.api.filter.dto.UpdateFilterRequestDto;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface FilterMapper {
    FilterMapper INSTANCE = Mappers.getMapper(FilterMapper.class);

    FilterResponseDto filterToFilterResponseDto(Filter filter);

    Filter createFilterRequestDtoToFilter(CreateFilterRequestDto createFilterRequestDto);

    Filter updateFilterRequestDtoToFilter(UpdateFilterRequestDto updateFilterRequestDto);
}
