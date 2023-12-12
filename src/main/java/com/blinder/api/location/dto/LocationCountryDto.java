package com.blinder.api.location.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationCountryDto {
    private String countryId;
    private String countryName;
    private String iso2;
}
