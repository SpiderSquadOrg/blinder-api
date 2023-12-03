package com.blinder.api.filter.service;

import com.blinder.api.filter.model.Filter;
import com.blinder.api.user.model.User;

public interface FilterService {
    Filter createDefaultFilterForUser(String userId);

    Filter getFilterByUserId(String userId);

    Filter getFilterById(String id);

    Filter updateFilter(String id, Filter updatedFilter);

    void resetFilter(String id);

}
