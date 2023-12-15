package com.blinder.api.user.dto;

import com.blinder.api.location.dto.CreateLocationDto;
import com.blinder.api.location.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String nickname;
    private String phoneNumber;
    private String genderId;
    private String roleId;
    private Date birthDate;
    private List<String> images;
    private CreateLocationDto location;
}
