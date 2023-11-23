package com.blinder.api.report.service;

import com.blinder.api.report.model.Report;
import org.springframework.data.domain.Page;

public interface ReportService {
    Report addReport(Report report);

    Page<Report> getReports(Integer page, Integer size);

    Report getReportById(String reportId);

    Report updateReport(String reportId, Report report);

    void deleteReport(String reportId);

    Page<Report> searchReportsByFilters(String reporterId, String reportedId, String reporterRole, String reportedRole, String reportedGender, String reporterGender, Integer page, Integer size, String sortBy,
                                        String sortDirection);
}
