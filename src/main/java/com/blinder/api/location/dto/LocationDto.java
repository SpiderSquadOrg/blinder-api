package com.blinder.api.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    /*private LocationCountryDto country;
    private LocationStateDto state;*/
    private String countryId;
    private String stateId;
}
