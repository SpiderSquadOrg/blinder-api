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
    public Filter updateFilter(String id, Filter updatedFilter) {
        Filter filterToUpdate = this.filterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Filter not found with id: " + id));
        updateFilterGenders(filterToUpdate, updatedFilter.getGenders());

        if (filterBusinessRules.checkLocationTypeIsValid(updatedFilter.getLocationType())) {
            if(!updatedFilter.getLocationId().equals("")){
                filterToUpdate.setLocationType(updatedFilter.getLocationType());
                filterToUpdate.setLocationId(updatedFilter.getLocationId());
            }
        }
        if(filterBusinessRules.checkAgeRangeIsValid(updatedFilter.getAgeLowerBound(), updatedFilter.getAgeUpperBound())){
            filterToUpdate.setAgeUpperBound(updatedFilter.getAgeUpperBound());
            filterToUpdate.setAgeLowerBound(updatedFilter.getAgeLowerBound());

        }
        this.filterRepository.save(filterToUpdate);
        return filterToUpdate;
    }

    @Override
    public void resetFilter(String id) {
        Filter filter = filterRepository.findById(id).orElseThrow();
        Set<Gender> allGenders = new HashSet<>(genderRepository.findAll());

        filter.setGenders(allGenders);
        filter.setAgeLowerBound(Filter.getDefaultAgeLowerBound());
        filter.setAgeUpperBound(Filter.getDefaultAgeUpperBound());
        filter.setLocationType(Filter.getDefaultLocationType());
        filter.setLocationId(Filter.getDefaultLocationId());

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
