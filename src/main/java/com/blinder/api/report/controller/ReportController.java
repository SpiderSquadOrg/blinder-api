package com.blinder.api.report.controller;

import com.blinder.api.report.dto.CreateReportRequestDto;
import com.blinder.api.report.dto.ReportResponseDto;
import com.blinder.api.report.dto.UpdateReportRequestDto;
import com.blinder.api.report.mapper.ReportMapper;
import com.blinder.api.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reports")
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping
    @Operation(summary = "Get all reports")
    public ResponseEntity<Page<ReportResponseDto>> getAllReports(@RequestParam(name = "page", required = false) Integer page,
                                                                 @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(ReportMapper.INSTANCE.reportToReportResponseDto(reportService.getReports(page, size)), HttpStatus.OK);
    }

    @GetMapping("/search")
    @Operation(summary = "Search report by name")
    public ResponseEntity<Page<ReportResponseDto>> searchReportByName(@RequestParam(name = "reporterId", required = false) String reporterId,
                                                                      @RequestParam(name = "reportedId", required = false) String reportedId,
                                                                      @RequestParam(name = "reporterRole", required = false) String reporterRole,
                                                                      @RequestParam(name = "reportedRole", required = false) String reportedRole,
                                                                      @RequestParam(name = "reportedGender", required = false) String reportedGender,
                                                                      @RequestParam(name = "reporterGender", required = false) String reporterGender,
                                                                      @RequestParam(name = "page", required = false) Integer page,
                                                                      @RequestParam(name = "size", required = false) Integer size,
                                                                      @RequestParam(name = "sortBy", required = false) String sortBy,
                                                                      @RequestParam(name = "sortDirection", required = false) String sortDirection) {

        return new ResponseEntity<>(ReportMapper.INSTANCE.reportToReportResponseDto(reportService.searchReportsByFilters(
                reporterId,
                reportedId,
                reporterRole,
                reportedRole,
                reportedGender,
                reporterGender,
                page,
                size,
                sortBy,
                sortDirection
        )), HttpStatus.OK);
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "Get report by id")
    public ResponseEntity<ReportResponseDto> getReportById(@PathVariable String reportId) {
        return new ResponseEntity<>(ReportMapper.INSTANCE.reportToReportResponseDto(reportService.getReportById(reportId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new report")
    public ResponseEntity<CreateReportRequestDto> addReport(@RequestBody @Valid  CreateReportRequestDto createReportRequestDto) {
        reportService.addReport(ReportMapper.INSTANCE.createReportRequestDtoToReport(createReportRequestDto));
        return new ResponseEntity<>(createReportRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{reportId}")
    @Operation(summary = "Update report")
    public ResponseEntity<UpdateReportRequestDto> updateReport(@PathVariable(name = "reportId") String reportId,
                                                               @RequestBody
                                                               @Valid UpdateReportRequestDto updateReportRequestDto) {
        reportService.updateReport(reportId, ReportMapper.INSTANCE.updateReportRequestDtoToReport(updateReportRequestDto));
        return new ResponseEntity<>(updateReportRequestDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{reportId}")
    @Operation(summary = "Delete report")
    public ResponseEntity<HttpStatus> deleteReport(@PathVariable(name = "reportId") String reportId) {
        reportService.deleteReport(reportId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
