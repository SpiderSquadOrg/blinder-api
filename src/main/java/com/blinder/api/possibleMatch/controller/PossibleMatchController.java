package com.blinder.api.possibleMatch.controller;
import com.blinder.api.possibleMatch.dto.PossibleMatchResponseDto;
import com.blinder.api.possibleMatch.mapper.PossibleMatchMapper;
import com.blinder.api.possibleMatch.model.PossibleMatchStatus;
import com.blinder.api.possibleMatch.service.PossibleMatchService;
import com.blinder.api.user.dto.UserResponseDto;
import com.blinder.api.user.mapper.UserMapper;
import com.blinder.api.user.model.User;
import com.blinder.api.user.security.auth.service.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/possibleMatches")
@RestController
@RequiredArgsConstructor
public class PossibleMatchController {
    private final PossibleMatchService possibleMatchService;
    private final UserAuthService userAuthService;

    @GetMapping
    @Operation(summary = "Get all possible matches")
    public ResponseEntity<List<PossibleMatchResponseDto>> getAllPossibleMatches(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getAllPossibleMatches(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get all possible matches by status")
    public ResponseEntity<List<PossibleMatchResponseDto>> getAllPossibleMatchesByStatus(@PathVariable PossibleMatchStatus status){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getAllPossibleMatchesByStatus(currentUser, status)), HttpStatus.OK);
    }

    @GetMapping("/matched")
    @Operation(summary = "Get matched users")
    public ResponseEntity<List<UserResponseDto>> getMatchedUsers(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(
                possibleMatchService.getMatchedUsers(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/liked")
    @Operation(summary = "Get liked users")
    public ResponseEntity<List<UserResponseDto>> getLikedUsers(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(
                possibleMatchService.getLikedUsers(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/disliked")
    @Operation(summary = "Get disliked users")
    public ResponseEntity<List<UserResponseDto>> getDislikedUsers(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(UserMapper.INSTANCE.userToUserResponseDto(
                possibleMatchService.getDislikedUsers(currentUser)), HttpStatus.OK);
    }

    @PostMapping("/like/{userId}")
    @Operation(summary = "")
    public ResponseEntity<String> likeUser(@PathVariable Long userId) {
        // Kullanıcının diğer kullanıcıyı beğenmesi işlemi
        // ...

        return new ResponseEntity<>("User liked successfully", HttpStatus.OK);
    }

    @PostMapping("/dislike/{userId}")
    public ResponseEntity<String> dislikeUser(@PathVariable Long userId) {
        // Kullanıcının diğer kullanıcıyı beğenmemesi işlemi
        // ...

        return new ResponseEntity<>("User disliked successfully", HttpStatus.OK);
    }
}
