package com.blinder.api.Music.mapper;
import com.blinder.api.Music.dto.MusicRequestDto;
import com.blinder.api.Music.model.Music;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MusicMapper {
    MusicMapper INSTANCE = Mappers.getMapper(MusicMapper.class);

    @Mapping(source = "spotifyId", target = "spotifyId")
    Music musicRequestDtoToMusic(MusicRequestDto musicRequestDto);
}
