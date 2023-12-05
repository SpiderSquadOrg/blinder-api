package com.blinder.api.user.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthDto {
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
}