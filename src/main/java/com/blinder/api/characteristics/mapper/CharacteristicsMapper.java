package com.blinder.api.characteristics.mapper;

import com.blinder.api.characteristics.dto.CharacteristicsResponseDto;
import com.blinder.api.characteristics.dto.CreateCharacteristicsRequestDto;
import com.blinder.api.characteristics.dto.UpdateCharacteristicsRequestDto;
import com.blinder.api.characteristics.model.Characteristics;
import com.blinder.api.report.dto.CreateReportRequestDto;
import com.blinder.api.report.dto.ReportResponseDto;
import com.blinder.api.report.dto.UpdateReportRequestDto;
import com.blinder.api.report.model.Report;
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
public interface CharacteristicsMapper {
    CharacteristicsMapper INSTANCE = Mappers.getMapper(CharacteristicsMapper.class);

    @Mapping(source = "user.id", target = "userId")
    List<CharacteristicsResponseDto> characteristicsToCharacteristicsResponseDto(List<Characteristics> characteristics);

    @Mapping(source = "user.id", target = "userId")
    CharacteristicsResponseDto characteristicsToCharacteristicsResponseDto(Characteristics characteristics);

    @Mapping(source = "user.id", target = "userId")
    Characteristics characteristicsResponseDtoToCharacteristics(CharacteristicsResponseDto characteristicsResponseDto);

    @Mapping(source = "user.id", target = "userId")
    Characteristics createCharacteristicsRequestDtoToCharacteristics(CreateCharacteristicsRequestDto createCharacteristicsRequestDto);

    @Mapping(source = "user.id", target = "userId")
    Characteristics updateCharacteristicsRequestDtoToCharacteristics(UpdateCharacteristicsRequestDto updateCharacteristicsRequestDto);

    @Mapping(source = "user.id", target = "userId")
    default Page<CharacteristicsResponseDto> characteristicsToCharacteristicsResponseDto(Page<Characteristics> characteristicsPage) {
        List<CharacteristicsResponseDto> responseDtoList = characteristicsToCharacteristicsResponseDto(characteristicsPage.getContent());
        return new PageImpl<>(responseDtoList, characteristicsPage.getPageable(), characteristicsPage.getTotalElements());
    }
}
