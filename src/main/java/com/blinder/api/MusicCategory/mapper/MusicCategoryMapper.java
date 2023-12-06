package com.blinder.api.MusicCategory.mapper;

import com.blinder.api.Music.mapper.MusicMapper;
import com.blinder.api.MusicCategory.dto.CreateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.model.MusicCategory;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MusicCategoryMapper {
    MusicCategoryMapper INSTANCE = Mappers.getMapper(MusicCategoryMapper.class);

    MusicCategory createMusicCategoryRequestDtoToMusicCategory(CreateMusicCategoryRequestDto createMusicCategoryRequestDto);
}