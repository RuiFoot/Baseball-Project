package io.github.ruifoot.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ruifoot.api.dto.auth.request.LoginRequest;
import io.github.ruifoot.api.dto.auth.request.RefreshTokenRequest;
import io.github.ruifoot.api.dto.auth.request.SignupRequest;
import io.github.ruifoot.api.test.BaseTest;
import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.github.ruifoot.domain.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AuthControllerTest extends BaseTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }


    @Test
    void login_ReturnsToken_WhenCredentialsAreValid() throws Exception {
        // 준비
        String email = "testuser@example.com";
        String password = "password123";
        LoginRequest loginRequest = new LoginRequest(email, password);

        JwtToken jwtToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken("mockAccessToken")
                .refreshToken("mockRefreshToken")
                .build();

        log.info("[DEBUG_LOG] 유효한 자격 증명으로 /auth/login 엔드포인트 테스트 중");
        when(authService.login(email, password)).thenReturn(jwtToken);

        // 실행 & 검증
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());

        log.info("[DEBUG_LOG] /auth/login 엔드포인트 테스트 통과");
    }

    @Test
    void login_ReturnsFail_WhenCredentialsAreInvalid() throws Exception {
        // 준비
        String email = "testuser@example.com";
        String password = "wrongpassword";
        LoginRequest loginRequest = new LoginRequest(email, password);

        log.info("[DEBUG_LOG] 유효하지 않은 자격 증명으로 /auth/login 엔드포인트 테스트 중");
        when(authService.login(email, password)).thenThrow(new RuntimeException("Invalid username or password"));

        // 실행 & 검증
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());

        log.info("[DEBUG_LOG] 유효하지 않은 자격 증명으로 /auth/login 엔드포인트 테스트 통과");
    }

    @Test
    void signup_ReturnsUser_WhenDataIsValid() throws Exception {
        // 준비
        String username = "newuser";
        String email = "newuser@example.com";
        String password = "password123";
        SignupRequest signupRequest = new SignupRequest(username, password, email);

        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);
        user.setEmail(email);

        log.info("[DEBUG_LOG] 유효한 데이터로 /auth/signup 엔드포인트 테스트 중");
        when(authService.register(username, email, password)).thenReturn(user);

        // 실행 & 검증
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isCreated());

        log.info("[DEBUG_LOG] /auth/signup 엔드포인트 테스트 통과");
    }

    @Test
    void signup_ReturnsFail_WhenUsernameExists() throws Exception {
        // 준비
        String username = "existinguser";
        String email = "newuser@example.com";
        String password = "password123";
        SignupRequest signupRequest = new SignupRequest(username, password, email);

        log.info("[DEBUG_LOG] 이미 존재하는 사용자 이름으로 /auth/signup 엔드포인트 테스트 중");
        when(authService.register(username, email, password)).thenThrow(new RuntimeException("Username already exists"));

        // 실행 & 검증
        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isConflict());


        log.info("[DEBUG_LOG] 이미 존재하는 사용자 이름으로 /auth/signup 엔드포인트 테스트 통과");
    }

    @Test
    void refresh_ReturnsNewToken_WhenRefreshTokenIsValid() throws Exception {
        // 준비
        String refreshToken = "valid-refresh-token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);

        JwtToken newToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken("new-access-token")
                .refreshToken("new-refresh-token")
                .build();

        log.info("[DEBUG_LOG] 유효한 리프레시 토큰으로 /auth/refresh 엔드포인트 테스트 중");
        when(authService.refreshToken(refreshToken)).thenReturn(newToken);

        // 실행 & 검증
        mockMvc.perform(post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andExpect(status().isOk());

        log.info("[DEBUG_LOG] /auth/refresh 엔드포인트 테스트 통과");
    }

    @Test
    void refresh_ReturnsFail_WhenRefreshTokenIsInvalid() throws Exception {
        // 준비
        String refreshToken = "invalid-refresh-token";
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken);

        log.info("[DEBUG_LOG] 유효하지 않은 리프레시 토큰으로 /auth/refresh 엔드포인트 테스트 중");
        when(authService.refreshToken(refreshToken)).thenThrow(new RuntimeException("Invalid refresh token"));

        // 실행 & 검증
        mockMvc.perform(post("/auth/refresh")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(refreshTokenRequest)))
                .andExpect(status().isUnauthorized());

        log.info("[DEBUG_LOG] 유효하지 않은 리프레시 토큰으로 /auth/refresh 엔드포인트 테스트 통과");
    }

    @Test
    void logout_ReturnsSuccess() throws Exception {
        // 준비
        String refreshToken = "valid-refresh-token";
        log.info("[DEBUG_LOG] /auth/logout 엔드포인트 테스트 중");

        when(authService.logout(refreshToken)).thenReturn(true);

        // 실행 & 검증
        mockMvc.perform(delete("/auth/logout")
                .param("token", refreshToken))
                .andExpect(status().isOk());

        log.info("[DEBUG_LOG] /auth/logout 엔드포인트 테스트 통과");
    }

    @Test
    void logout_ReturnsFail_WhenTokenIsInvalid() throws Exception {
        // 준비
        String refreshToken = "invalid-refresh-token";
        log.info("[DEBUG_LOG] 유효하지 않은 토큰으로 /auth/logout 엔드포인트 테스트 중");

        when(authService.logout(refreshToken)).thenThrow(new RuntimeException("Invalid token"));

        // 실행 & 검증
        mockMvc.perform(delete("/auth/logout")
                .param("token", refreshToken))
                .andExpect(status().isUnauthorized());

        log.info("[DEBUG_LOG] 유효하지 않은 토큰으로 /auth/logout 엔드포인트 테스트 통과");
    }
}
