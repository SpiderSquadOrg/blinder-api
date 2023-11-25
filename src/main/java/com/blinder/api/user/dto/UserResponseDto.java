package com.blinder.api.user.dto;

import com.blinder.api.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private LocationDto location;
    private boolean isMatched;
    private boolean isBanned;
    /*
    private String gender;
    private String role;
    private String birthDate;
    private String profileImage;
    private String coverImage;
    private String region;
    private String country;
    private String city;
    */
}
