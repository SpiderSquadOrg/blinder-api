package com.blinder.api.user.mapper;

import com.blinder.api.user.dto.CreateUserRequestDto;
import com.blinder.api.user.dto.UpdateUserRequestDto;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.model.User;
import com.blinder.api.user.security.UserAuthDetails;
import com.blinder.api.user.security.dto.RegisterRequestDto;
import com.blinder.api.user.security.dto.UserAuthDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
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

    @Mapping(source = "genderId", target = "gender.id")
    User registerRequestDtoToUser(RegisterRequestDto registerRequestDto);

    @Mapping(source = "roleId", target = "role.id")
    @Mapping(source = "genderId", target = "gender.id")
    User updateUserRequestDtoToUser(UpdateUserRequestDto updateUserRequestDto);

    default Page<UserResponseDto> userToUserResponseDto(Page<User> userPage) {
        List<UserResponseDto> responseDtoList = userToUserResponseDto(userPage.getContent());
        return new PageImpl<>(responseDtoList, userPage.getPageable(), userPage.getTotalElements());
    }

    @Mapping(target = "authorities", source = "authorities", qualifiedByName = "mapAuthorities")
    UserAuthDto userToUserDto(UserAuthDetails userAuthDetails);

    @Named("mapAuthorities")
    List<GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities);
}

