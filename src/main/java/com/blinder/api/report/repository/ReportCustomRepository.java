package com.blinder.api.report.repository;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.report.model.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportCustomRepository {
    Page<Report> searchReportsByFilter(String reporterId, String reportedId, String reporterRole, String reportedRole, String reportedGender, String reporterGender, Pageable pageable, SortCriteria sortCriteria);
}
