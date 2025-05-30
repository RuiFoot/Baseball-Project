package io.github.ruifoot.core.service.auth;

import io.github.ruifoot.common.exception.CustomException;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.core.CoreTestApplication;
import io.github.ruifoot.core.test.BaseTest;
import io.github.ruifoot.domain.model.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.infrastructure.cache.redis.RedisService;
import io.github.ruifoot.infrastructure.security.jwt.JwtService;
import io.github.ruifoot.infrastructure.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = CoreTestApplication.class)
public class AuthServiceImplTest extends BaseTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private RedisService redisService;
    @Mock
    private PasswordEncoder passwordEncoder;

    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, jwtService, jwtTokenProvider, redisService, passwordEncoder);
    }

    @Test
    void getUsername_ReturnsUsername_WhenUserExists() {
        // 준비
        long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setUsername("testuser");
        log.info("[DEBUG_LOG] 사용자 ID로 getUsername 테스트 중: {}", userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // 실행
        String username = authService.getUsername(userId);

        // 검증
        assertThat(username).isEqualTo("testuser");
        log.info("[DEBUG_LOG] 반환된 사용자 이름: {}", username);
        verify(userRepository).findById(userId);
    }

    @Test
    void getUsername_ReturnsIdAsString_WhenUserDoesNotExist() {
        // 준비
        long userId = 999L;
        log.info("[DEBUG_LOG] 존재하지 않는 사용자 ID로 getUsername 테스트 중: {}", userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // 실행
        String username = authService.getUsername(userId);

        // 검증
        assertThat(username).isEqualTo("999");
        log.info("[DEBUG_LOG] 반환된 사용자 이름: {}", username);
        verify(userRepository).findById(userId);
    }

    @Test
    void register_ReturnsUser_WhenUsernameAndEmailAreUnique() {
        // 준비
        String username = "newuser";
        String email = "newuser@example.com";
        String password = "password123";
        log.info("[DEBUG_LOG] 사용자 이름과 이메일로 회원가입 테스트 중: {}, 이메일: {}", username, email);

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(false);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword123");
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> {
            Users savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        // 실행
        Users registeredUser = authService.register(username, email, password);

        log.info("[DEBUG_LOG] 등록된 사용자: id={}, 사용자 이름={}, 이메일={}, 비밀번호={}, 권한={}",
                registeredUser.getId(), registeredUser.getUsername(), registeredUser.getEmail(), registeredUser.getPasswordHash(), registeredUser.getRole());
        // 검증
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getUsername()).isEqualTo(username);
        assertThat(registeredUser.getEmail()).isEqualTo(email);
        assertThat(registeredUser.getPasswordHash()).isNotNull();
        assertThat(registeredUser.getRole()).isEqualTo("USER");
        assertThat(registeredUser.isAdminApproved()).isFalse();

        log.info("[DEBUG_LOG] 등록된 사용자: id={}, 사용자 이름={}, 이메일={}", 
                registeredUser.getId(), registeredUser.getUsername(), registeredUser.getEmail());

        verify(userRepository).existsByUsername(username);
        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(Users.class));
    }

    @Test
    void register_ThrowsException_WhenUsernameExists() {
        // 준비
        String username = "existinguser";
        String email = "newuser@example.com";
        String password = "password123";
        log.info("[DEBUG_LOG] 이미 존재하는 사용자 이름으로 회원가입 테스트 중: {}", username);

        when(userRepository.existsByUsername(username)).thenReturn(true);

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.register(username, email, password)
        );

        assertThat(exception.getMessage()).isEqualTo("Username already exists");
        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(userRepository).existsByUsername(username);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void register_ThrowsException_WhenEmailExists() {
        // 준비
        String username = "newuser";
        String email = "existing@example.com";
        String password = "password123";
        log.info("[DEBUG_LOG] 이미 존재하는 이메일로 회원가입 테스트 중: {}", email);

        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.existsByEmail(email)).thenReturn(true);

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.register(username, email, password)
        );

        assertThat(exception.getMessage()).isEqualTo("Email already exists");
        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(userRepository).existsByUsername(username);
        verify(userRepository).existsByEmail(email);
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    void login_ReturnsJwtToken_WhenCredentialsAreValid() {
        // 준비
        String email = "testuser@example.com";
        String password = "password123";

        JwtToken expectedToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken("access-token")
                .refreshToken("refresh-token")
                .build();

        log.info("[DEBUG_LOG] 이메일로 로그인 테스트 중: {}", email);
        when(jwtService.signIn(email, password)).thenReturn(expectedToken);

        // 실행
        JwtToken result = authService.login(email, password);

        // 검증
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("access-token");
        assertThat(result.getRefreshToken()).isEqualTo("refresh-token");
        log.info("[DEBUG_LOG] 로그인 성공: 토큰={}", result.getAccessToken());

        verify(jwtService).signIn(email, password);
    }

    @Test
    void login_ThrowsException_WhenCredentialsAreInvalid() {
        // 준비
        String email = "nonexistentuser@example.com";
        String password = "password123";
        log.info("[DEBUG_LOG] 잘못된 자격 증명으로 로그인 테스트 중: {}", email);

        RuntimeException mockException = new RuntimeException("Invalid username or password");
        when(jwtService.signIn(email, password)).thenThrow(mockException);

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.login(email, password)
        );

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(jwtService).signIn(email, password);
    }

    @Test
    void refreshToken_ReturnsNewToken_WhenRefreshTokenIsValid() {
        // 준비
        String refreshToken = "valid-refresh-token";

        JwtToken expectedToken = JwtToken.builder()
                .grantType("Bearer")
                .accessToken("new-access-token")
                .refreshToken("new-refresh-token")
                .build();

        log.info("[DEBUG_LOG] 유효한 리프레시 토큰으로 토큰 갱신 테스트 중");
        when(jwtService.refreshToken(refreshToken)).thenReturn(expectedToken);

        // 실행
        JwtToken result = authService.refreshToken(refreshToken);

        // 검증
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("new-access-token");
        assertThat(result.getRefreshToken()).isEqualTo("new-refresh-token");
        log.info("[DEBUG_LOG] 토큰 갱신 성공: 새 토큰={}", result.getAccessToken());

        verify(jwtService).refreshToken(refreshToken);
    }

    @Test
    void refreshToken_ThrowsException_WhenRefreshTokenIsInvalid() {
        // 준비
        String refreshToken = "invalid-refresh-token";
        log.info("[DEBUG_LOG] 유효하지 않은 리프레시 토큰으로 토큰 갱신 테스트 중");

        RuntimeException mockException = new RuntimeException("Invalid refresh token");
        when(jwtService.refreshToken(refreshToken)).thenThrow(mockException);

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.refreshToken(refreshToken)
        );

        assertThat(exception.getMessage()).isEqualTo("Invalid refresh token");
        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(jwtService).refreshToken(refreshToken);
    }

    @Test
    void logout_ReturnsTrue_WhenRefreshTokenIsValid() {
        // 준비
        String refreshToken = "valid-refresh-token";
        log.info("[DEBUG_LOG] 유효한 리프레시 토큰으로 로그아웃 테스트 중");

        when(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true);
        when(redisService.hasKey(refreshToken)).thenReturn(true);
        doNothing().when(redisService).deleteValues(refreshToken);

        // 실행
        boolean result = authService.logout(refreshToken);

        // 검증
        assertThat(result).isTrue();
        log.info("[DEBUG_LOG] 로그아웃 성공");

        verify(jwtTokenProvider).validateToken(refreshToken);
        verify(redisService).hasKey(refreshToken);
        verify(redisService).deleteValues(refreshToken);
    }

    @Test
    void logout_ReturnsFalse_WhenRefreshTokenNotInRedis() {
        // 준비
        String refreshToken = "valid-refresh-token-not-in-redis";
        log.info("[DEBUG_LOG] Redis에 없는 리프레시 토큰으로 로그아웃 테스트 중");

        when(jwtTokenProvider.validateToken(refreshToken)).thenReturn(true);
        when(redisService.hasKey(refreshToken)).thenReturn(false);

        // 실행
        boolean result = authService.logout(refreshToken);

        // 검증
        assertThat(result).isFalse();
        log.info("[DEBUG_LOG] 예상대로 로그아웃 실패 (토큰이 Redis에 없음)");

        verify(jwtTokenProvider).validateToken(refreshToken);
        verify(redisService).hasKey(refreshToken);
        verify(redisService, never()).deleteValues(refreshToken);
    }

    @Test
    void logout_ThrowsException_WhenRefreshTokenIsInvalid() {
        // 준비
        String refreshToken = "invalid-refresh-token";
        log.info("[DEBUG_LOG] 유효하지 않은 리프레시 토큰으로 로그아웃 테스트 중");

        RuntimeException mockException = new CustomException(ResponseCode.INVALID_TOKEN, ResponseCode.INVALID_TOKEN.getMessage());
        when(jwtTokenProvider.validateToken(refreshToken)).thenThrow(mockException);

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.logout(refreshToken)
        );

        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(jwtTokenProvider).validateToken(refreshToken);
        verify(redisService, never()).hasKey(refreshToken);
        verify(redisService, never()).deleteValues(refreshToken);
    }
}
