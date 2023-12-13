package com.blinder.api.filter.controller;
import com.blinder.api.filter.dto.UpdateFilterRequestDto;
import com.blinder.api.filter.dto.FilterResponseDto;
import com.blinder.api.filter.mapper.FilterMapper;
import com.blinder.api.filter.model.Filter;
import com.blinder.api.filter.service.FilterService;
import com.blinder.api.user.mapper.GenderMapper;
import com.blinder.api.user.model.Gender;
import com.blinder.api.user.model.User;
import com.blinder.api.user.security.auth.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.Set;

@RequestMapping("/filter")
@RestController
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;
    private final UserAuthService userAuthService;

    @GetMapping("/byUser/{userId}")
    @Operation(summary = "Get filter by userId")
    public ResponseEntity<FilterResponseDto> getFilterByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(FilterMapper.INSTANCE.filterToFilterResponseDto(filterService.getFilterByUserId(userId)), HttpStatus.OK);
    }

    @GetMapping("/{filterId}")
    @Operation(summary = "Get filter by filter id")
    public ResponseEntity<FilterResponseDto> getFilterById(@PathVariable String filterId) {
        return new ResponseEntity<>(FilterMapper.INSTANCE.filterToFilterResponseDto(filterService.getFilterById(filterId)), HttpStatus.OK);
    }

    @PutMapping("/{filterId}")
    @Operation(summary = "Update filter for user")
    public ResponseEntity<FilterResponseDto> updateFilter(@PathVariable String filterId, @RequestBody UpdateFilterRequestDto updateFilterRequestDto) {
        Filter updatedFilter = FilterMapper.INSTANCE.updateFilterRequestDtoToFilter(updateFilterRequestDto);
        Filter updatedFilterResult = filterService.updateFilter(filterId, updatedFilter);
        return new ResponseEntity<>(FilterMapper.INSTANCE.filterToFilterResponseDto(updatedFilterResult), HttpStatus.OK);
    }

    @PutMapping("/reset/{filterId}")
    @Operation(summary = "Reset filter for user")
    public ResponseEntity<FilterResponseDto> resetFilter(@PathVariable String filterId) {
        filterService.resetFilter(filterId);
        return new ResponseEntity<>(FilterMapper.INSTANCE.filterToFilterResponseDto(filterService.getFilterById(filterId)), HttpStatus.OK);
    }
}
