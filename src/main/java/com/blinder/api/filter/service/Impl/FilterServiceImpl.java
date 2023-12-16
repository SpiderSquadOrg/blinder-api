package com.blinder.api.filter.service.Impl;

import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.repository.FilterRepository;
import com.blinder.api.filter.rules.FilterBusinessRules;
import com.blinder.api.filter.service.FilterService;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.GenderRepository;
import com.blinder.api.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;
    private final GenderRepository genderRepository;
    private final UserRepository userRepository;
    private final FilterBusinessRules filterBusinessRules;

    @Override
    public Filter createDefaultFilterForUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Set<Gender> allGenders = new HashSet<>(genderRepository.findAll());
        Filter filter = filterRepository.save(new Filter(user, allGenders));
        user.setFilter(filter);
        userRepository.save(user);
        return filter;
    }

    @Override
    public Filter getFilterByUserId(String userId) {
        return this.filterRepository.findByUserId(userId).orElseThrow();
    }

    @Override
    public Filter getFilterById(String filterId) {
        return this.filterRepository.findById(filterId).orElseThrow();
    }

    @Override
    @Transactional
    public Filter updateFilter(String userId, Filter updatedFilter) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        String filterId = user.getFilter().getId();

        Filter filterToUpdate = this.filterRepository.findById(filterId).orElseThrow(() -> new EntityNotFoundException("Filter not found with id: " + filterId));

        updateFilterGenders(filterToUpdate, updatedFilter.getGenders());

        if(updatedFilter.getCountryIso2() != null && !updatedFilter.getCountryIso2().isEmpty()){
            filterToUpdate.setCountryIso2(updatedFilter.getCountryIso2());
        }

        if(updatedFilter.getStateIso2() != null && !updatedFilter.getStateIso2().isEmpty()){
            filterToUpdate.setStateIso2(updatedFilter.getStateIso2());
        }

        if(filterBusinessRules.checkAgeRangeIsValid(filterToUpdate, updatedFilter.getAgeLowerBound(), updatedFilter.getAgeUpperBound())){
            if(updatedFilter.getAgeLowerBound() != 0){
                filterToUpdate.setAgeLowerBound(updatedFilter.getAgeLowerBound());
            }
            if(updatedFilter.getAgeUpperBound() != 0){
                filterToUpdate.setAgeUpperBound(updatedFilter.getAgeUpperBound());
            }
        }

        this.filterRepository.save(filterToUpdate);
        return filterToUpdate;
    }

    @Override
    public void resetFilter(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        String filterId = user.getFilter().getId();

        Filter filter = filterRepository.findById(filterId).orElseThrow(() -> new EntityNotFoundException("Filter not found with id: " + filterId));
        Set<Gender> allGenders = new HashSet<>(genderRepository.findAll());
        Set<Gender> filterGenders = filter.getGenders();

        for (Gender gender : allGenders) {
            if (!filterGenders.contains(gender)) {
                filterGenders.add(gender);
            }
        }

        filter.setGenders(filterGenders);
        filter.setAgeLowerBound(Filter.getDefaultAgeLowerBound());
        filter.setAgeUpperBound(Filter.getDefaultAgeUpperBound());
        filter.setCountryIso2(Filter.getDefaultCountryIso2());
        filter.setStateIso2(Filter.getDefaultStateIso2());

        filterRepository.save(filter);
    }

    private void updateFilterGenders(Filter filterToUpdate, Set<Gender> updatedGenders) {
        Set<Gender> existingGenders = filterToUpdate.getGenders();

        if (updatedGenders != null && !updatedGenders.isEmpty()) {
            existingGenders.clear();

            for (Gender gender : updatedGenders) {
                Gender existingGender = genderRepository.findByName(gender.getName());

                if (existingGender != null) {
                    existingGenders.add(existingGender);
                }
            }
        }
    }


}
