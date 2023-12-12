package com.blinder.api.filter.rules;

import com.blinder.api.filter.model.LocationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FilterBusinessRules {

    public boolean checkLocationTypeIsValid(LocationType locationType) {
        if (locationType == null) {
            return false;
        }

        for (LocationType validType : LocationType.values()) {
            if (validType.equals(locationType)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAgeRangeIsValid(int lowerBound, int upperBound) {
        return lowerBound >= 18 && lowerBound < upperBound;
    }
}
