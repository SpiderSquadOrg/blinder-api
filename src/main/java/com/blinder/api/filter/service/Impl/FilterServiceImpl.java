package com.blinder.api.filter.service.Impl;

import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.model.LocationType;
import com.blinder.api.filter.repository.FilterRepository;
import com.blinder.api.filter.service.FilterService;
import com.blinder.api.user.dto.GenderRequestDto;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.GenderRepository;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class FilterServiceImpl implements FilterService {
    private final FilterRepository filterRepository;
    private final GenderRepository genderRepository;
    private final UserRepository userRepository;
    private final UserAuthService userAuthService;

    @Override
    public Filter createDefaultFilterForUser(String userId) {
        //String activeUserId = this.userAuthService.getActiveUser().getId();
        //userService.getUserById(activeUserId).getGender();
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
    public Filter getFilterById(String id) {
        return this.filterRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Filter updateFilter(String id, Filter updatedFilter) {
        Filter filterToUpdate = this.filterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Filter not found with id: " + id));
        updateGenders(filterToUpdate, updatedFilter.getGenders());

        if (updatedFilter.getLocationType() != null && isValidLocationType(updatedFilter.getLocationType())) {
            if(!updatedFilter.getLocationName().equals("")){ // TO DO: && Name doğru mu kontrol edilmeli!!!
                filterToUpdate.setLocationType(updatedFilter.getLocationType());
                filterToUpdate.setLocationName(updatedFilter.getLocationName());
            }
        }
        if((updatedFilter.getAgeLowerBound() >= 18) && (updatedFilter.getAgeLowerBound() < updatedFilter.getAgeUpperBound())){
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
        filter.setLocationName(Filter.getDefaultLocationName());

        filterRepository.save(filter);
    }

    private void updateGenders(Filter filterToUpdate, Set<Gender> updatedGenders) {
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

    private boolean isValidLocationType(LocationType locationType) {
        for (LocationType validType : LocationType.values()) {
            if (validType.equals(locationType)) {
                return true;
            }
        }
        return false;
    }
}
