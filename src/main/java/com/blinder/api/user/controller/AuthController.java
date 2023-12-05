package com.blinder.api.user.controller;

import com.blinder.api.user.mapper.UserMapper;
import com.blinder.api.user.model.User;
import com.blinder.api.user.security.auth.service.AuthenticationService;
import com.blinder.api.user.security.auth.service.UserAuthService;
import com.blinder.api.user.security.dto.AuthenticationRequest;
import com.blinder.api.user.security.dto.AuthenticationResponse;
import com.blinder.api.user.security.dto.RegisterRequestDto;
import com.blinder.api.user.security.dto.UserAuthDto;
import com.blinder.api.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final UserAuthService userAuthService;

    @GetMapping("/active")
    @Operation(summary = "Get active user")
    public ResponseEntity<UserAuthDto> activeUser(){
        return ResponseEntity.ok(UserMapper.INSTANCE.userToUserDto(userAuthService.getActiveUser()));
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    @Operation(summary = "Register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequestDto registerRequestDto
    ) {
        this.userService.register(UserMapper.INSTANCE.registerRequestDtoToUser(registerRequestDto));

        return ResponseEntity.ok(authenticationService.authenticate(
                AuthenticationRequest.builder()
                .username(registerRequestDto.getUsername())
                .password(registerRequestDto.getPassword())
                .build()));
    }
}
