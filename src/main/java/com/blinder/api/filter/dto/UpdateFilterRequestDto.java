package com.blinder.api.filter.dto;

import com.blinder.api.user.dto.GenderRequestDto;
import com.blinder.api.user.dto.GenderResponseDto;
import com.blinder.api.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFilterRequestDto {
    private List<GenderRequestDto> genders;
    //private LocationFilter locationFilter;
    private int ageLowerBound;
    private int ageUpperBound;
}
