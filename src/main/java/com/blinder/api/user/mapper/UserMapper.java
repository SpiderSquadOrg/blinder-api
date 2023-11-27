package com.blinder.api.user.mapper;

import com.blinder.api.user.dto.CreateUserRequestDto;
import com.blinder.api.user.dto.UpdateUserRequestDto;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserResponseDto> userToUserResponseDto(List<User> users);

    UserResponseDto userToUserResponseDto(User user);

    User userResponseDtoToUser(UserResponseDto userResponseDto);

    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "genderId", target = "gender.id")
    User createUserRequestDtoToUser(CreateUserRequestDto createUserRequestDto);

    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "genderId", target = "gender.id")
    User updateUserRequestDtoToUser(UpdateUserRequestDto updateUserRequestDto);

    default Page<UserResponseDto> userToUserResponseDto(Page<User> userPage) {
        List<UserResponseDto> responseDtoList = userToUserResponseDto(userPage.getContent());
        return new PageImpl<>(responseDtoList, userPage.getPageable(), userPage.getTotalElements());
    }
}

