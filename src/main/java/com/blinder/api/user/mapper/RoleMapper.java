package com.blinder.api.user.mapper;

import com.blinder.api.user.dto.RoleRequestDto;
import com.blinder.api.user.dto.RoleResponseDto;
import com.blinder.api.user.model.Role;
import org.mapstruct.Mapper;
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
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    List<RoleResponseDto> roleToRoleResponseDto(List<Role> roles);

    RoleResponseDto roleToRoleResponseDto(Role role);
    Role roleResponseDtoToRole(RoleResponseDto roleResponseDto);

    Role roleRequestDtoToRole(RoleRequestDto roleRequestDto);
    

    default Page<RoleResponseDto> roleToRoleResponseDto(Page<Role> rolePage) {
        List<RoleResponseDto> responseDtoList = roleToRoleResponseDto(rolePage.getContent());
        return new PageImpl<>(responseDtoList, rolePage.getPageable(), rolePage.getTotalElements());
    }
}