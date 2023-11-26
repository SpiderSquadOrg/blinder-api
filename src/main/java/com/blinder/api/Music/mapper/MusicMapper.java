package com.blinder.api.Music.mapper;

import com.blinder.api.Music.dto.CreateMusicRequestDto;
import com.blinder.api.Music.dto.MusicResponseDto;
import com.blinder.api.Music.dto.UpdateMusicRequestDto;
import com.blinder.api.Music.model.Music;
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
public interface MusicMapper {
    MusicMapper INSTANCE = Mappers.getMapper(MusicMapper.class);


    List<MusicResponseDto> musicToMusicResponseDto(List<Music> musicCategories);


    MusicResponseDto musicToMusicResponseDto(Music music);

    Music musicResponseDtoToMusic(MusicResponseDto musicResponseDto);


    @Mapping(source = "musicCategoryId", target = "musicCategory.id")
    Music createMusicRequestDtoToMusic(CreateMusicRequestDto createMusicRequestDto);


    @Mapping(source = "musicCategoryId", target = "musicCategory.id")
    Music updateMusicRequestDtoToMusic(UpdateMusicRequestDto updateMusicRequestDto);


    default Page<MusicResponseDto> musicToMusicResponseDto(Page<Music> musicPage) {
        List<MusicResponseDto> responseDtoList = musicToMusicResponseDto(musicPage.getContent());
        return new PageImpl<>(responseDtoList, musicPage.getPageable(), musicPage.getTotalElements());
    }
}
