package com.blinder.api.MusicCategory.mapper;

import com.blinder.api.MusicCategory.dto.CreateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.dto.MusicCategoryResponseDto;
import com.blinder.api.MusicCategory.dto.UpdateMusicCategoryRequestDto;
import com.blinder.api.MusicCategory.model.MusicCategory;
import org.mapstruct.Mapper;
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
public interface MusicCategoryMapper {
    MusicCategoryMapper INSTANCE = Mappers.getMapper(MusicCategoryMapper.class);


    List<MusicCategoryResponseDto> musicCategoryToMusicCategoryResponseDto(List<MusicCategory> musicCategories);


    MusicCategoryResponseDto musicCategoryToMusicCategoryResponseDto(MusicCategory musicCategory);

    MusicCategory musicCategoryResponseDtoToMusicCategory(MusicCategoryResponseDto musicCategoryResponseDto);


    MusicCategory createMusicCategoryRequestDtoToMusicCategory(CreateMusicCategoryRequestDto categoryRequestDto);


   MusicCategory updateMusicCategoryRequestDtoToMusicCategory(UpdateMusicCategoryRequestDto updateMusicCategoryRequestDto);


    default Page<MusicCategoryResponseDto> musicCategoryToMusicCategoryResponseDto(Page<MusicCategory> musicCategoryPage) {
        List<MusicCategoryResponseDto> responseDtoList = musicCategoryToMusicCategoryResponseDto(musicCategoryPage.getContent());
        return new PageImpl<>(responseDtoList, musicCategoryPage.getPageable(), musicCategoryPage.getTotalElements());
    }


}