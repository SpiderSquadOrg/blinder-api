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
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<List<PossibleMatchResponseDto>> getAllPossibleMatches(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getAllPossibleMatches(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/{status}")
    @Operation(summary = "Get all possible matches by status")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<List<PossibleMatchResponseDto>> getAllPossibleMatchesByStatus(@PathVariable PossibleMatchStatus status){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getAllPossibleMatchesByStatus(currentUser, status)), HttpStatus.OK);
    }

    @GetMapping("/matched")
    @Operation(summary = "Get matched users")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<List<PossibleMatchResponseDto>> getMatchedUsers(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getMatchedUsers(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/liked")
    @Operation(summary = "Get liked users")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<List<PossibleMatchResponseDto>> getLikedUsers(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getLikedUsers(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/users_who_like")
    @Operation(summary = "Get users who like")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<List<PossibleMatchResponseDto>> getUsersWhoLike(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getUsersWhoLike(currentUser)), HttpStatus.OK);
    }

    @GetMapping("/disliked")
    @Operation(summary = "Get disliked users")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<List<PossibleMatchResponseDto>> getDislikedUsers(){
        User currentUser = userAuthService.getActiveUser().getUser();
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.getDislikedUsers(currentUser)), HttpStatus.OK);
    }

    @PostMapping("/like/{possibleMatchId}")
    @Operation(summary = "Like possible match")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<PossibleMatchResponseDto> likeUser(@PathVariable String possibleMatchId) {
        return new ResponseEntity<>(PossibleMatchMapper.INSTANCE.possibleMatchToPossibleMatchResponseDto(
                possibleMatchService.likePossibleMatch(possibleMatchId)), HttpStatus.OK);
    }

    @PostMapping("/dislike/{possibleMatchId}")
    @Operation(summary = "Dislike possible match")
    // TO DO: @PreAuthorize("hasRole('ROLE_ADMIN') or principal.id == #userId")
    public ResponseEntity<String> dislikeUser(@PathVariable String possibleMatchId) {
        possibleMatchService.dislikePossibleMatch(possibleMatchId);
        return new ResponseEntity<>("User disliked successfully", HttpStatus.OK);
    }
}
