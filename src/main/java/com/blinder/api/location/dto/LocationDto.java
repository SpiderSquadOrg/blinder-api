package com.blinder.api.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    private String countryId;
    private String countryName;
    private String countryIso2;
    private String stateId;
    private String stateName;
    private String stateIso2;
}
