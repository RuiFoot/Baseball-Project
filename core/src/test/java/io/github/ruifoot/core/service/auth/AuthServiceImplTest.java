package io.github.ruifoot.core.service.auth;

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

    private AuthServiceImpl authService;

    private JwtService jwtService;
    private JwtTokenProvider jwtTokenProvider;
    private RedisService redisService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        authService = new AuthServiceImpl(userRepository, jwtService, jwtTokenProvider,redisService,passwordEncoder);
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
        when(userRepository.save(any(Users.class))).thenAnswer(invocation -> {
            Users savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        // 실행
        Users registeredUser = authService.register(username, email, password);

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
    void login_ReturnsUser_WhenCredentialsAreValid() {

        // 준비
        String username = "testuser";
        String password = "password123";
        String hashedPassword = passwordEncoder.encode(password);

        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);

        log.info("[DEBUG_LOG] 사용자 이름으로 로그인 테스트 중: {}", username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // 실행
        JwtToken loggedInUser = authService.login(username, password);

        // 검증
        assertThat(loggedInUser).isNotNull();
        log.info("[DEBUG_LOG] 로그인 성공: 토큰={}", loggedInUser.getAccessToken());

        verify(userRepository).findByUsername(username);
    }

    @Test
    void login_ThrowsException_WhenUsernameNotFound() {
        // 준비
        String username = "nonexistentuser";
        String password = "password123";
        log.info("[DEBUG_LOG] 존재하지 않는 사용자 이름으로 로그인 테스트 중: {}", username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.login(username, password)
        );

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(userRepository).findByUsername(username);
    }

    @Test
    void login_ThrowsException_WhenPasswordIsInvalid() {
        // 준비
        String username = "testuser";
        String correctPassword = "password123";
        String wrongPassword = "wrongpassword";
        String hashedPassword = passwordEncoder.encode(correctPassword);

        Users user = new Users();
        user.setId(1L);
        user.setUsername(username);
        user.setPasswordHash(hashedPassword);

        log.info("[DEBUG_LOG] 잘못된 비밀번호로 로그인 테스트 중 (사용자: {})", username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // 실행 & 검증
        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            authService.login(username, wrongPassword)
        );

        assertThat(exception.getMessage()).isEqualTo("Invalid username or password");
        log.info("[DEBUG_LOG] 예상대로 예외 발생: {}", exception.getMessage());

        verify(userRepository).findByUsername(username);
    }
}
