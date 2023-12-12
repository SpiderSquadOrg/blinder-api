package com.blinder.api.MovieCategory.mapper;

import com.blinder.api.MovieCategory.dto.MovieCategoryRequestDto;
import com.blinder.api.MovieCategory.model.MovieCategory;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MovieCategoryMapper {
    MovieCategoryMapper INSTANCE = Mappers.getMapper(MovieCategoryMapper.class);

    MovieCategory movieCategoryRequestDtoToMovieCategory(MovieCategoryRequestDto movieCategoryRequestDto);
}