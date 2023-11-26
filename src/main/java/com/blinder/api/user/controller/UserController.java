package com.blinder.api.user.controller;

import com.blinder.api.user.dto.CreateUserRequestDto;
import com.blinder.api.user.dto.UpdateUserRequestDto;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.mapper.UserMapper;
import com.blinder.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserResponseDto> getReportById(@PathVariable String userId) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.getUserById(userId)), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@RequestParam(name = "page", required = false) Integer page,
                                                             @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.getUsers(page, size)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add user")
    public ResponseEntity<CreateUserRequestDto> addUser(@RequestBody @Valid CreateUserRequestDto createUserRequestDto) {
        this.userService.addUser(UserMapper.INSTANCE.createUserRequestDtoToUser(createUserRequestDto));
        return new ResponseEntity<>(createUserRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user by id")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable String userId,
                                                          @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.updateUserById(userId, UserMapper.INSTANCE.updateUserRequestDtoToUser(updateUserRequestDto))), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by id")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable String userId) {
        this.userService.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{userId}/ban")
    @Operation(summary = "Ban user by id")
    public ResponseEntity<UserResponseDto> banUserById(@PathVariable String userId) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.banUserById(userId)), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/unban")
    @Operation(summary = "Unban user by id")
    public ResponseEntity<UserResponseDto> unbanUserById(@PathVariable String userId) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.unbanUserById(userId)), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/block")
    @Operation(summary = "Block user by id")
    public ResponseEntity<UserResponseDto> blockUserById(@PathVariable String userId) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.blockUserById(userId)), HttpStatus.OK);
    }

    @PatchMapping("/{userId}/unblock")
    @Operation(summary = "Unblock user by id")
    public ResponseEntity<UserResponseDto> unblockUserById(@PathVariable String userId) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(this.userService.unblockUserById(userId)), HttpStatus.OK);
    }
}
