package com.blinder.api.location.mapper;

import com.blinder.api.location.dto.LocationDto;
import com.blinder.api.location.model.Location;
import com.blinder.api.user.dto.CreateUserRequestDto;
import com.blinder.api.user.dto.UpdateUserRequestDto;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.model.User;
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
public interface LocationMapper {
    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

    List<LocationDto> locationToLocationDto(List<Location> locations);

    LocationDto locationToLocationDto(Location location);
    Location locationDtoToLocation(LocationDto locationDto);


    default Page<LocationDto> locationToLocationDto(Page<Location> locationPage) {
        List<LocationDto> responseDtoList = locationToLocationDto(locationPage.getContent());
        return new PageImpl<>(responseDtoList, locationPage.getPageable(), locationPage.getTotalElements());
    }
}

