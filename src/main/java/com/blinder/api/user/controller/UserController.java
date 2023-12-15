package com.blinder.api.user.controller;

import com.blinder.api.location.mapper.LocationCustomMapper;
import com.blinder.api.user.dto.CreateUserRequestDto;
import com.blinder.api.user.dto.UpdateUserRequestDto;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.mapper.UserMapper;
import com.blinder.api.user.model.User;
import com.blinder.api.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final LocationCustomMapper locationCustomMapper;

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

    @GetMapping("/search")
    @Operation(summary = "Search users")
    public ResponseEntity<Page<UserResponseDto>> searchUsers(@RequestParam(name = "page", required = false) Integer page,
                                                             @RequestParam(name = "size", required = false) Integer size,
                                                             @RequestParam(name = "email", required = false) String email,
                                                             @RequestParam(name = "name", required = false) String name,
                                                             @RequestParam(name = "surname", required = false) String surname,
                                                             @RequestParam(name = "username", required = false) String username,
                                                             @RequestParam(name = "roleNames", required = false) String[] roleNames,
                                                             @RequestParam(name = "genderNames", required = false) String[] genderNames,
                                                             @RequestParam(name = "ageLowerBound", required = false) String ageLowerBound,
                                                             @RequestParam(name = "ageUpperBound", required = false) String ageUpperBound,
                                                             @RequestParam(name = "region", required = false) String region,
                                                             @RequestParam(name = "country", required = false) String country,
                                                             @RequestParam(name = "city", required = false) String city,
                                                             @RequestParam(name = "isMatched", required = false) Boolean isMatched,
                                                             @RequestParam(name = "isBanned", required = false) Boolean isBanned,
                                                             @RequestParam(name = "sortBy", required = false) String sortBy,
                                                             @RequestParam(name = "sortDirection", required = false) String sortDirection
                                                             ) {
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(
                this.userService.searchUsers(
                                page, size,
                                email, name, surname, username,
                                roleNames, genderNames,
                                ageLowerBound, ageUpperBound,
                                region, country, city,
                                isMatched, isBanned,
                                sortBy, sortDirection
                        )), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add user")
    public ResponseEntity<CreateUserRequestDto> addUser(@RequestBody @Valid CreateUserRequestDto createUserRequestDto) throws JsonProcessingException {
        User user =UserMapper.INSTANCE.createUserRequestDtoToUser(createUserRequestDto);
        user.setLocation(locationCustomMapper.createLocationDtoToLocation(createUserRequestDto.getLocation()));
        this.userService.addUser(user);
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

    @GetMapping("/{userId}/images")
    @Operation(summary = "Get user images by id")
    public ResponseEntity<List<String>> getUserImagesByChatInfo(@PathVariable String userId, @RequestParam String chatId, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        return new ResponseEntity<>(this.userService.getUserImagesByChatInfo(userId, chatId, token), HttpStatus.OK);
    }

    @GetMapping("/chats/{chatId}")
    @Operation(summary = "Get remaining chat time")
    public ResponseEntity<String> getRemainingChatTime(@PathVariable String chatId, @RequestHeader("Authorization") String token) throws JsonProcessingException {
        return new ResponseEntity<>(this.userService.getRemainingChatTime(chatId, token), HttpStatus.OK);
    }
}
