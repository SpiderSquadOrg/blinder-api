package com.blinder.api.filter.dto;

import com.blinder.api.filter.model.LocationType;
import com.blinder.api.user.dto.GenderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterResponseDto {
    private String id;
    private List<GenderResponseDto> genders;
    private LocationType locationType;
    private String locationName;
    private int ageLowerBound;
    private int ageUpperBound;
}
