package com.blinder.api.filter.dto;

import com.blinder.api.user.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFilterRequestDto {
    private List<Gender> genders;
    //private LocationFilter locationFilter;
    private int ageLowerBound;
    private int ageUpperBound;
}
