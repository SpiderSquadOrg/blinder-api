package com.blinder.api.report.service.impl;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.common.sort.SortDirection;
import com.blinder.api.report.model.Report;
import com.blinder.api.report.repository.ReportCustomRepository;
import com.blinder.api.report.repository.ReportRepository;
import com.blinder.api.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final ReportCustomRepository reportCustomRepository;
    @Override
    public Report addReport(Report report) {
        return reportRepository.save(report);
    }

    @Override
    public Page<Report> getReports(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.reportRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Report getReportById(String reportId) {
        return this.reportRepository.findById(reportId).orElseThrow();
    }

    @Override
    public Report updateReport(String reportId, Report report) {
        Report reportToUpdate = this.reportRepository.findById(reportId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(report);

        BeanUtils.copyProperties(report, reportToUpdate, nullPropertyNames.toArray(new String[0]));

        this.reportRepository.save(reportToUpdate);
        return reportToUpdate;
    }

    @Override
    public void deleteReport(String reportId) {
        this.reportRepository.deleteById(reportId);
    }

    @Override
    public Page<Report> searchReportsByFilters(String reporterId,
                                               String reportedId,
                                               String reporterRole,
                                               String reportedRole,
                                               String reportedGender,
                                               String reporterGender,
                                               Integer page,
                                               Integer size,
                                               String sortBy,
                                               String sortDirection) {
        if(Objects.isNull(page) || Objects.isNull(size)){
            page = 0;
            size = Integer.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(page, size);
        if (Objects.isNull(sortBy) || sortBy.isEmpty()) {
            sortBy = "name";
        }

        SortDirection so;
        if (Objects.nonNull(sortDirection) && sortDirection.equals("ASCENDING")) {
            so = SortDirection.ASCENDING;
        } else {
            so = SortDirection.DESCENDING;
        }

        SortCriteria sortCriteria = new SortCriteria(sortBy, so);
        return this.reportCustomRepository.searchReportsByFilter(reporterId, reportedId, reporterRole, reportedRole, reportedGender, reporterGender, pageable, sortCriteria);
    }
}
