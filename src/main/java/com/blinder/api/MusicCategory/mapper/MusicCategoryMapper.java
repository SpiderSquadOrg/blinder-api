package com.blinder.api.MusicCategory.mapper;

import com.blinder.api.MusicCategory.dto.MusicCategoryResponseDto;
import com.blinder.api.MusicCategory.model.MusicCategory;
import com.blinder.api.report.dto.ReportResponseDto;
import com.blinder.api.report.model.Report;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;


public interface MusicCategoryMapper {
    MusicCategoryMapper INSTANCE = Mappers.getMapper(MusicCategoryMapper.class);

    @Mapping(source = "musicCategory.id", target = "musicCategoryId")
    List<MusicCategoryResponseDto> musicCategoryToMusicCategoryResponseDto(List<MusicCategory> musicCategories);

    @Mapping(source = "musicCategory.id", target = "musicCategoryId")
    MusicCategoryResponseDto musicCategoryToMusicCategoryResponseDto(MusicCategory musicCategory);

    @Mapping(source = "musicCategoryId", target = "musicCategory.id")
    MusicCategory MusicCategoryResponseDtoToMusicCategory(MusicCategoryResponseDto musicCategoryResponseDto);



}