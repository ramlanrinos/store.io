package com.storeio.userservice;

import com.storeio.userservice.dto.AuthResponse;
import com.storeio.userservice.dto.LoginRequest;
import com.storeio.userservice.dto.RegisterRequest;
import com.storeio.userservice.dto.UserProfileResponse;
import com.storeio.userservice.entity.Role;
import com.storeio.userservice.entity.User;
import com.storeio.userservice.repository.RoleRepository;
import com.storeio.userservice.repository.UserRepository;
import com.storeio.userservice.security.JwtTokenProvider;
import com.storeio.userservice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthService authService;

    private User sampleUser;
    private Role customerRole;

    @BeforeEach
    void setUp() {
        customerRole = Role.builder().id(1L).name("ROLE_CUSTOMER").build();
        sampleUser = User.builder()
                .id(101L)
                .email("john.doe@example.com")
                .passwordHash("hashedPassword123")
                .firstName("John")
                .lastName("Doe")
                .phone("+1234567890")
                .active(true)
                .roles(new HashSet<>(Collections.singletonList(customerRole)))
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void register_Success() {
        RegisterRequest request = RegisterRequest.builder()
                .email("john.doe@example.com")
                .password("password123")
                .firstName("John")
                .lastName("Doe")
                .phone("+1234567890")
                .build();

        when(userRepository.existsByEmail("john.doe@example.com")).thenReturn(false);
        when(roleRepository.findByName("ROLE_CUSTOMER")).thenReturn(Optional.of(customerRole));
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(sampleUser);

        UserProfileResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals("John", response.getFirstName());
        assertTrue(response.getRoles().contains("ROLE_CUSTOMER"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void login_Success() {
        LoginRequest request = LoginRequest.builder()
                .email("john.doe@example.com")
                .password("password123")
                .build();

        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("password123", "hashedPassword123")).thenReturn(true);
        when(jwtTokenProvider.generateToken(sampleUser)).thenReturn("sample.jwt.token");
        when(jwtTokenProvider.getExpirationMs()).thenReturn(86400000L);

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("sample.jwt.token", response.getToken());
        assertEquals("john.doe@example.com", response.getEmail());
        assertEquals(101L, response.getUserId());
    }
}
