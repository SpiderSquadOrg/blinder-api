package com.blinder.api.user.dto;

import com.blinder.api.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String nickname;
    private LocationDto location;
    private boolean isMatched;
    private boolean isBanned;
    private GenderResponseDto gender;
    private RoleResponseDto role;
    private List<String> images;
    private int age;
    /*
    private String profileImage;
    private String coverImage;
    */
}
