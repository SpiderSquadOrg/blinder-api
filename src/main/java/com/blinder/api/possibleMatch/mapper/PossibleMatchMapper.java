package com.blinder.api.possibleMatch.mapper;

import com.blinder.api.possibleMatch.dto.PossibleMatchRequestDto;
import com.blinder.api.possibleMatch.dto.PossibleMatchResponseDto;
import com.blinder.api.possibleMatch.model.PossibleMatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PossibleMatchMapper {
    PossibleMatchMapper INSTANCE = Mappers.getMapper(PossibleMatchMapper.class);

    List<PossibleMatchResponseDto> possibleMatchToPossibleMatchResponseDto(List<PossibleMatch> possibleMatches);

    @Mapping(source = "possibleMatch.to.id", target = "to")
    @Mapping(source = "possibleMatch.from.id", target = "from")
    PossibleMatchResponseDto possibleMatchToPossibleMatchResponseDto(PossibleMatch possibleMatch);

    List<PossibleMatch> possibleMatchRequestDtoToPossibleMatch(List<PossibleMatchRequestDto> possibleMatchRequestDtos);

    PossibleMatch possibleMatchRequestDtoToPossibleMatch(PossibleMatchRequestDto possibleMatchRequestDto);
}
