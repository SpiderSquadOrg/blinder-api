package com.blinder.api.user.dto;

import com.blinder.api.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicResponseDto {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String nickname;
    private LocationDto location;
    private boolean isMatched;
    private boolean isBanned;
    private GenderResponseDto gender;
    private List<String> images;
    private int age;
}
