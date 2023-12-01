package com.blinder.api.filter.controller;
import com.blinder.api.filter.dto.CreateFilterRequestDto;
import com.blinder.api.filter.dto.UpdateFilterRequestDto;
import com.blinder.api.filter.dto.FilterResponseDto;
import com.blinder.api.filter.mapper.FilterMapper;
import com.blinder.api.filter.service.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/filter")
@RestController
@RequiredArgsConstructor
public class FilterController {
    private final FilterService filterService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get filter by userId")
    public ResponseEntity<FilterResponseDto> getFilterByUserId(@PathVariable String userId) {
        return new ResponseEntity<>(FilterMapper.INSTANCE.filterToFilterResponseDto(filterService.getFilterByUserId(userId)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get filter by id")
    public ResponseEntity<FilterResponseDto> getFilterById(@PathVariable String id) {
        return new ResponseEntity<>(FilterMapper.INSTANCE.filterToFilterResponseDto(filterService.getFilterById(id)), HttpStatus.OK);
    }

    @PostMapping("/{userId}")
    @Operation(summary = "Create default filter for user")
    public ResponseEntity<CreateFilterRequestDto> createFilterForUser(@PathVariable String userId,
                                                                      @RequestBody CreateFilterRequestDto createFilterRequestDto) {
        filterService.createDefaultFilterForUser(userId);
        return new ResponseEntity<>(createFilterRequestDto, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update filter for user")
    public ResponseEntity<UpdateFilterRequestDto> updateFilterForUser(@RequestBody UpdateFilterRequestDto updateFilterRequestDto) {
        filterService.updateFilter(FilterMapper.INSTANCE.updateFilterRequestDtoToFilter(updateFilterRequestDto));
        return new ResponseEntity<>(updateFilterRequestDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Reset filter for user")
    public ResponseEntity<HttpStatus> resetFilterForUser(@PathVariable String userId) {
        filterService.resetFilter(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
