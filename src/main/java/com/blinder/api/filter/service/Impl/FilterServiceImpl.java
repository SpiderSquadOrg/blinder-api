package com.blinder.api.filter.service.Impl;

import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.repository.FilterRepository;
import com.blinder.api.filter.service.FilterService;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import com.blinder.api.user.repository.GenderRepository;
import com.blinder.api.user.repository.UserRepository;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
        return filterRepository.save(new Filter(user, allGenders));
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
    public Filter updateFilter(Filter updatedFilter) {
        String userId = updatedFilter.getUser().getId();

        Filter filterToUpdate = this.filterRepository.findByUserId(userId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(updatedFilter);

        BeanUtils.copyProperties(updatedFilter, filterToUpdate, nullPropertyNames.toArray(new String[0]));

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

        filterRepository.save(filter);
    }











    /*private final FilterRepository filterRepository;

    @Override
    public Filter getFilterByUserId(String userId) {
        return this.filterRepository.findByUserId(userId).orElseThrow();
    }

    @Override
    public Filter getFilterById(String id) {
        return this.filterRepository.findById(id).orElseThrow();
    }

    @Override
    public Filter createFilterForUser(String userId, Filter filter) {
        Filter existingFilter = filterRepository.findByUserId(userId).orElse(null);
        if (existingFilter != null) {
            return updateFilterForUser(userId, filter);
        } else {
            // Eğer kullanıcının henüz bir filtresi yoksa, yeni filtreyi oluştur
            filter.setUser(new User(userId)); // User nesnesini oluşturarak filtreye set et
            return filterRepository.save(filter);
        }
    }

    @Override
    public Filter updateFilterForUser(String userId, Filter updatedFilter) {
        Filter filterToUpdate = this.filterRepository.findByUserId(userId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(updatedFilter);

        BeanUtils.copyProperties(updatedFilter, filterToUpdate, nullPropertyNames.toArray(new String[0]));

        this.filterRepository.save(filterToUpdate);
        return filterToUpdate;
    }

    @Override
    public void resetFilterForUser(String userId) {
        filterRepository.findByUserId(userId).ifPresent(filterRepository::delete);
    }*/
}
