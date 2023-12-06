package com.blinder.api.Music.mapper;

import com.blinder.api.Music.dto.CreateMusicRequestDto;
import com.blinder.api.Music.dto.MusicResponseDto;
import com.blinder.api.Music.model.Music;
import com.blinder.api.user.dto.RoleRequestDto;
import com.blinder.api.user.dto.RoleResponseDto;
import com.blinder.api.user.model.Role;
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

    @Mapping(source = "spotifyId", target = "spotifyId")
    Music createMusicRequestDtoToMusic(CreateMusicRequestDto createMusicRequestDto);
}
