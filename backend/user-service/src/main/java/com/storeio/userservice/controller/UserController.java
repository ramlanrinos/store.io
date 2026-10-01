package com.storeio.userservice.controller;

import com.storeio.userservice.dto.UserProfileResponse;
import com.storeio.userservice.security.JwtTokenProvider;
import com.storeio.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long userIdHeader,
            Authentication authentication) {

        if (userIdHeader != null) {
            return ResponseEntity.ok(userService.getUserProfileById(userIdHeader));
        }

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return ResponseEntity.ok(userService.getUserProfileByEmail(email));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestParam("token") String token) {
        Map<String, Object> response = new HashMap<>();
        boolean isValid = jwtTokenProvider.validateToken(token);
        
        response.put("valid", isValid);
        if (isValid) {
            response.put("userId", jwtTokenProvider.getUserIdFromToken(token));
            response.put("email", jwtTokenProvider.getEmailFromToken(token));
        }

        return ResponseEntity.ok(response);
    }
}
