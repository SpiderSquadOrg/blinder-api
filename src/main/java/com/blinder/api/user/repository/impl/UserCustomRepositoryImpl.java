package com.blinder.api.user.repository.impl;

import com.blinder.api.common.sort.SortCriteria;
import com.blinder.api.common.sort.SortDirection;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.UserCustomRepository;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {
    private final EntityManager entityManager;

    //TODO: Implement your logic to search users by filter
    @Override
    public Page<User> searchUsersByFilter(
            String email, String name, String surname, String username,
            String[] roleNames, String[] genderNames, String ageLowerBound, String ageUpperBound,
            String region, String country, String city, Boolean isMatched, Boolean isBanned,
            Pageable pageable, SortCriteria sortCriteria) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        // Add filtering predicates based on input parameters
        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
        }
        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        }

        if (surname != null && !surname.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("surname")), "%" + surname.toLowerCase() + "%"));
        }

        if (username != null && !username.isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
        }

        if (roleNames != null && roleNames.length > 0) {
            predicates.add(root.get("role").get("name").in((Object[]) roleNames));
        }

        if (genderNames != null && genderNames.length > 0) {
            predicates.add(root.get("gender").get("name").in((Object[]) genderNames));
        }

        if (ageLowerBound != null && !ageLowerBound.isEmpty()) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("birthDate"), calculateBirthDate(ageLowerBound)));
        }

        if (ageUpperBound != null && !ageUpperBound.isEmpty()) {
            predicates.add(cb.lessThanOrEqualTo(root.get("birthDate"), calculateBirthDate(ageUpperBound)));
        }

        if (region != null && !region.isEmpty()) {
            predicates.add(cb.equal(root.get("location").get("region"), region));
        }

        if (country != null && !country.isEmpty()) {
            predicates.add(cb.equal(root.get("location").get("country"), country));
        }

        if (city != null && !city.isEmpty()) {
            predicates.add(cb.equal(root.get("location").get("city"), city));
        }

        if(isMatched != null) predicates.add(cb.equal(root.get("isMatched"), isMatched));
        if(isBanned != null) predicates.add(cb.equal(root.get("isBanned"), isBanned));

        query.where(predicates.toArray(new Predicate[0]));

        // Sorting
        if (sortCriteria != null) {
            String sortBy = sortCriteria.getSortBy();
            Sort.Direction sortDirection = sortCriteria.getSortDirection() == SortDirection.ASCENDING ?
                    Sort.Direction.ASC : Sort.Direction.DESC;

            Path<?> sortPath = switch (sortBy) {
                case "createdDate" -> root.get("createdDate");
                case "updateDate" -> root.get("updateDate");
                case "name" -> root.get("name");
                case "surname" -> root.get("surname");
                case "username" -> root.get("username");
                case "email" -> root.get("email");
                case "birthDate" -> root.get("birthDate");
                case "location.region" -> root.get("location").get("region");
                case "location.country" -> root.get("location").get("country");
                case "location.city" -> root.get("location").get("city");
                default -> root.get(sortBy);
            };

            query.orderBy(sortDirection == Sort.Direction.ASC ?
                    cb.asc(sortPath) : cb.desc(sortPath));
        }

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        long totalItems = typedQuery.getResultList().size();

        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalItems);
    }

    private Date calculateBirthDate(String age) {
        int ageInt = Integer.parseInt(age);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -ageInt);
        return cal.getTime();
    }
}
