package com.blinder.api.report.mapper;

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
public interface ReportMapper {
    ReportMapper INSTANCE = Mappers.getMapper(ReportMapper.class);

    @Mapping(source = "reporter.id", target = "reporterId")
    List<ReportResponseDto> reportToReportResponseDto(List<Report> reports);

    @Mapping(source = "reporter.id", target = "reporterId")
    ReportResponseDto reportToReportResponseDto(Report report);

    @Mapping(source = "reporterId", target = "reporter.id")
    Report reportResponseDtoToReport(ReportResponseDto reportResponseDto);

    @Mapping(source = "reporterId", target = "reporter.id")
    Report createReportRequestDtoToReport(CreateReportRequestDto createReportRequestDto);

    @Mapping(source = "reporterId", target = "reporter.id")
    Report updateReportRequestDtoToReport(UpdateReportRequestDto updateReportRequestDto);

    @Mapping(source = "reporter.id", target = "reporterId")
    default Page<ReportResponseDto> reportToReportResponseDto(Page<Report> reportPage) {
        List<ReportResponseDto> responseDtoList = reportToReportResponseDto(reportPage.getContent());
        return new PageImpl<>(responseDtoList, reportPage.getPageable(), reportPage.getTotalElements());
    }
}
