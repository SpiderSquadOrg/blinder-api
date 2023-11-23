package com.blinder.api.report.repository.impl;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.common.sort.SortDirection;
import com.blinder.api.report.model.Report;
import com.blinder.api.report.repository.ReportCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportCustomRepository {
    private final EntityManager entityManager;
    @Override
    public Page<Report> searchReportsByFilter(String reporterId, String reportedId, String reporterRole, String reportedRole, String reportedGender, String reporterGender, Pageable pageable, SortCriteria sortCriteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Report> query = cb.createQuery(Report.class);
        Root<Report> root = query.from(Report.class);

        List<Predicate> predicates = new ArrayList<>();

        if (reporterId != null && !reporterId.isEmpty()) {
            predicates.add(cb.equal(root.join("reporter").get("id"), reporterId));
        }

        if (reportedId != null && !reportedId.isEmpty()) {
            predicates.add(cb.equal(root.join("reported").get("id"), reportedId));
        }

        if (reporterRole != null && !reporterRole.isEmpty()) {
            predicates.add(cb.equal(root.join("reporter").join("role").get("name"), reporterRole));
        }

        if (reportedRole != null && !reportedRole.isEmpty()) {
            predicates.add(cb.equal(root.join("reported").join("role").get("name"), reportedRole));
        }

        if (reporterGender != null && !reporterGender.isEmpty()) {
            predicates.add(cb.equal(root.join("reporter").join("gender").get("name"), reporterGender));
        }

        if (reportedGender != null && !reportedGender.isEmpty()) {
            predicates.add(cb.equal(root.join("reported").join("gender").get("name"), reportedGender));
        }

        query.where(predicates.toArray(new Predicate[0]));

        // Sorting
        if (sortCriteria != null) {
            String sortBy = sortCriteria.getSortBy();
            Sort.Direction sortDirection = sortCriteria.getSortDirection() == SortDirection.ASCENDING ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            Path<?> sortPath = switch (sortBy) {
                case "createdDate" -> root.get("createdDate");
                case "updateDate" -> root.get("updateDate");
                default -> root.get(sortBy);
            };

            query.orderBy(sortDirection == Sort.Direction.ASC ?
                    cb.asc(sortPath) : cb.desc(sortPath));
        }

        TypedQuery<Report> typedQuery = entityManager.createQuery(query);
        long totalItems = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Report> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalItems);
    }
}
