package io.github.ruifoot.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ruifoot.api.dto.auth.request.LoginRequest;
import io.github.ruifoot.api.dto.auth.request.SignupRequest;
import io.github.ruifoot.api.test.BaseTest;
import io.github.ruifoot.domain.model.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.github.ruifoot.domain.service.users.UserService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    void me_ReturnsUsername() throws Exception {
        // 준비
        String username = "testuser";
        log.info("[DEBUG_LOG] /auth/me 엔드포인트 테스트 중");
        when(userService.getUsername(anyLong())).thenReturn(username);

        // 실행 & 검증
        mockMvc.perform(get("/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("SUCCESS"))
                .andExpect(jsonPath("$.data").value(username));

        log.info("[DEBUG_LOG] /auth/me 엔드포인트 테스트 통과");
    }

    @Test
    void login_ReturnsUser_WhenCredentialsAreValid() throws Exception {
        // 준비
        String username = "testuser";
        String password = "password123";
        LoginRequest loginRequest = new LoginRequest(username, password);

        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);

        JwtToken jwtToken;

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("LOGIN_SUCCESS"))
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.accessToken").value("mockAccessToken"))
                .andExpect(jsonPath("$.data.refreshToken").value("mockRefreshToken"));


        log.info("[DEBUG_LOG] /auth/login 엔드포인트 테스트 통과");
    }

    @Test
    void login_ReturnsFail_WhenCredentialsAreInvalid() throws Exception {
        // 준비
        String username = "testuser";
        String password = "wrongpassword";
        LoginRequest loginRequest = new LoginRequest(username, password);

        log.info("[DEBUG_LOG] 유효하지 않은 자격 증명으로 /auth/login 엔드포인트 테스트 중");
        when(authService.login(username, password)).thenThrow(new RuntimeException("Invalid username or password"));

        // 실행 & 검증
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("INVALID_PASSWORD"));

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USER_CREATE_SUCCESS"))
                .andExpect(jsonPath("$.data.username").value(username))
                .andExpect(jsonPath("$.data.email").value(email));

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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("DUPLICATED_USER"));

        log.info("[DEBUG_LOG] 이미 존재하는 사용자 이름으로 /auth/signup 엔드포인트 테스트 통과");
    }

    @Test
    void logout_ReturnsSuccess() throws Exception {
        // 준비
        log.info("[DEBUG_LOG] /auth/logout 엔드포인트 테스트 중");

        // 실행 & 검증
        mockMvc.perform(delete("/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("LOGOUT_SUCCESS"));

        log.info("[DEBUG_LOG] /auth/logout 엔드포인트 테스트 통과");
    }
}
