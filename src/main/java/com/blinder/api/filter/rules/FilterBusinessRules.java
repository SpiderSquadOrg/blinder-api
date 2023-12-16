package com.blinder.api.filter.rules;

import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.model.LocationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FilterBusinessRules {

    /*public boolean checkLocationTypeIsValid(LocationType locationType) {
        if (locationType == null) {
            return false;
        }

        for (LocationType validType : LocationType.values()) {
            if (validType.equals(locationType)) {
                return true;
            }
        }
        return false;
    }*/

    public boolean checkAgeRangeIsValid(Filter filterToUpdate, int lowerBound, int upperBound) {

        int lowerBoundCheck = lowerBound;
        int upperBoundCheck = upperBound;

        if(lowerBound == 0){
            lowerBoundCheck = filterToUpdate.getAgeLowerBound();
        }

        if(upperBound == 0){
            upperBoundCheck = filterToUpdate.getAgeUpperBound();
        }

        return lowerBoundCheck >= 18 && lowerBoundCheck < upperBoundCheck;
    }
}
