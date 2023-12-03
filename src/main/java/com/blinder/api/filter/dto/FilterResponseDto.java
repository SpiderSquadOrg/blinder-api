package com.blinder.api.filter.dto;

import com.blinder.api.user.dto.GenderResponseDto;
import com.blinder.api.user.model.Gender;
import jakarta.validation.constraints.NotBlank;
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
    //private LocationFilter locationFilter;
    private int ageLowerBound;
    private int ageUpperBound;
}
