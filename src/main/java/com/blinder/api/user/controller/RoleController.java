package com.blinder.api.user.controller;

import com.blinder.api.user.dto.RoleRequestDto;
import com.blinder.api.user.dto.RoleResponseDto;
import com.blinder.api.user.mapper.RoleMapper;
import com.blinder.api.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/roles")
@RestController
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Get all roles")
    public ResponseEntity<Page<RoleResponseDto>> getAllRoles(@RequestParam(name = "page", required = false) Integer page,
                                                               @RequestParam(name = "size", required = false) Integer size){
        return new ResponseEntity<>(RoleMapper.INSTANCE.roleToRoleResponseDto(roleService.getRoles(page, size)), HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    @Operation(summary = "Get role by id")
    public ResponseEntity<RoleResponseDto> getReportById(@PathVariable String roleId) {
        return new ResponseEntity<>(RoleMapper.INSTANCE.roleToRoleResponseDto(roleService.getRoleById(roleId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add role")
    public ResponseEntity<RoleRequestDto> addRole(@RequestBody RoleRequestDto roleRequestDto) {
        this.roleService.addRole(RoleMapper.INSTANCE.roleRequestDtoToRole(roleRequestDto));
        return new ResponseEntity<>(roleRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    @Operation(summary = "Update role by id")
    public ResponseEntity<RoleResponseDto> updateRoleById(@PathVariable String roleId,
                                                           @RequestBody RoleRequestDto roleRequestDto) {
        return new ResponseEntity<>(RoleMapper.INSTANCE.roleToRoleResponseDto(this.roleService.updateRole(roleId, RoleMapper.INSTANCE.roleRequestDtoToRole(roleRequestDto))), HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}")
    @Operation(summary = "Delete role by id")
    public ResponseEntity<HttpStatus> deleteRoleById(@PathVariable String roleId) {
        this.roleService.deleteRole(roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
