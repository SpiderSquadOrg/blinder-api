package com.blinder.api.TVSeries.mapper;
import com.blinder.api.TVSeries.dto.TVSeriesRequestDto;
import com.blinder.api.TVSeries.model.TVSeries;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface TVSeriesMapper {
    TVSeriesMapper INSTANCE = Mappers.getMapper(TVSeriesMapper.class);

    TVSeries tvSeriesRequestDtoToTVSeries(TVSeriesRequestDto tvSeriesRequestDto);
}
