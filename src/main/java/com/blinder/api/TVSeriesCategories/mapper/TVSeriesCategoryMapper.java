package com.blinder.api.TVSeriesCategories.mapper;

import com.blinder.api.TVSeriesCategories.dto.TVSeriesCategoryRequestDto;
import com.blinder.api.TVSeriesCategories.model.TVSeriesCategory;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TVSeriesCategoryMapper {
    TVSeriesCategoryMapper INSTANCE = Mappers.getMapper(TVSeriesCategoryMapper.class);

    TVSeriesCategory tvSeriesCategoryRequestDtoToTvSeriesCategory(TVSeriesCategoryRequestDto tvSeriesCategoryRequestDto);
}
