package com.storeio.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

    private String token;
    
    @Builder.Default
    private String tokenType = "Bearer";
    
    private Long expiresIn;
    private Long userId;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
}
