package com.blinder.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {
    private String username;
    private String email;
    private String name;
    private String surname;
    private String phoneNumber;
    private String genderId;
    private String roleId;
    private Date birthDate;
    private String profileImage;
    private String coverImage;
    private String region;
    private String country;
    private String city;
}
