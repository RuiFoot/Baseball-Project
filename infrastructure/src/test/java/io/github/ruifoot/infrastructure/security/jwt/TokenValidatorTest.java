package io.github.ruifoot.infrastructure.security.jwt;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.persistence.entity.User;
import io.github.ruifoot.infrastructure.test.BaseTest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;

import java.security.Key;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = InfrastructureTestApplication.class)
public class TokenValidatorTest extends BaseTest {

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest request;

    private TokenValidator tokenValidator;

    @BeforeEach
    void setUp() {
        jwtTokenProvider = mock(JwtTokenProvider.class);
        tokenValidator = new TokenValidator(jwtTokenProvider);
    }

    @Test
    void getAuthentication_ReturnsNull_WhenTokenIsInvalid() {
        // 준비
        // Create an invalid token but with correct format (2 periods)
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
        log.info("[DEBUG_LOG] 유효하지 않은 토큰으로 테스트 중: {}", invalidToken);
        when(jwtTokenProvider.validateToken(invalidToken)).thenReturn(false);

        // 실행
        Authentication authentication = tokenValidator.getAuthentication(invalidToken);

        // 검증
        assertThat(authentication).isNull();
        log.info("[DEBUG_LOG] 유효하지 않은 토큰에 대해 예상대로 인증 결과가 null입니다");
    }

    @Test
    void getAuthentication_ReturnsAuthentication_WhenTokenIsValid() {
        // 준비
        // Create a valid JWT token with exactly 2 period characters
        String testSecret = "testSecretKeyForJwtSigningThatIsLongEnough";
        Key testKey = Keys.hmacShaKeyFor(testSecret.getBytes());

        String userId = "user123";
        // Generate a real JWT token for testing
        String validToken = Jwts.builder()
                .setSubject(userId)
                .claim("authorities", "USER")
                .signWith(testKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("[DEBUG_LOG] 유효한 토큰으로 테스트 중: {}", validToken);
        log.info("[DEBUG_LOG] 예상 사용자 ID: {}", userId);
        log.info("[DEBUG_LOG] 테스트용 서명 키 생성됨");

        when(jwtTokenProvider.validateToken(validToken)).thenReturn(true);

        // Mock the parseClaims method to return valid claims
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(testKey)
                .build()
                .parseClaimsJws(validToken)
                .getBody();
        when(jwtTokenProvider.parseClaims(validToken)).thenReturn(claims);

        log.info("[DEBUG_LOG] JwtTokenProvider.getKey() 메서드가 테스트 키를 반환하도록 설정됨");
        log.info("[DEBUG_LOG] JwtTokenProvider.getKey() : " + testKey);

        // 실행
        Authentication authentication = tokenValidator.getAuthentication(validToken);

        // 검증
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isInstanceOf(User.class);
        User userPrincipal = (User) authentication.getPrincipal();
        assertThat(userPrincipal.getUsername()).isEqualTo(userId);
        log.info("[DEBUG_LOG] 인증 결과: principal={}, authorities={}", 
                authentication.getPrincipal(), 
                authentication.getAuthorities());
    }

    @Test
    void resolveToken_ReturnsToken_WhenAuthorizationHeaderIsValid() {
        // 준비
        // Create a properly formatted JWT token
        String testSecret = "testSecretKeyForJwtSigningThatIsLongEnough";
        Key testKey = Keys.hmacShaKeyFor(testSecret.getBytes());

        String token = Jwts.builder()
                .setSubject("testUser")
                .claim("authorities", "USER")
                .signWith(testKey, SignatureAlgorithm.HS256)
                .compact();

        String authHeader = "Bearer " + token;
        log.info("[DEBUG_LOG] Authorization 헤더로 테스트 중: {}", authHeader);
        when(request.getHeader("Authorization")).thenReturn(authHeader);

        // 실행
        String resolvedToken = tokenValidator.resolveToken(request);

        // 검증
        assertThat(resolvedToken).isEqualTo(token);
        log.info("[DEBUG_LOG] 추출된 토큰: {}", resolvedToken);
    }

    @Test
    void resolveToken_ReturnsNull_WhenAuthorizationHeaderIsInvalid() {
        // 준비
        String invalidHeader = "Invalid token";
        log.info("[DEBUG_LOG] 유효하지 않은 Authorization 헤더로 테스트 중: {}", invalidHeader);
        when(request.getHeader("Authorization")).thenReturn(invalidHeader);

        // 실행
        String resolvedToken = tokenValidator.resolveToken(request);

        // 검증
        assertThat(resolvedToken).isNull();
        log.info("[DEBUG_LOG] 유효하지 않은 헤더에 대해 예상대로 추출된 토큰이 null입니다");
    }

    @Test
    void resolveToken_ReturnsNull_WhenAuthorizationHeaderIsNull() {
        // 준비
        log.info("[DEBUG_LOG] null인 Authorization 헤더로 테스트 중");
        when(request.getHeader("Authorization")).thenReturn(null);

        // 실행
        String resolvedToken = tokenValidator.resolveToken(request);

        // 검증
        assertThat(resolvedToken).isNull();
        log.info("[DEBUG_LOG] null인 헤더에 대해 예상대로 추출된 토큰이 null입니다");
    }

    @Test
    void validateToken_DelegatesTo_JwtTokenProvider() {
        // 준비
        // Create a properly formatted JWT token
        String testSecret = "testSecretKeyForJwtSigningThatIsLongEnough";
        Key testKey = Keys.hmacShaKeyFor(testSecret.getBytes());

        String token = Jwts.builder()
                .setSubject("testUser")
                .claim("authorities", "USER")
                .signWith(testKey, SignatureAlgorithm.HS256)
                .compact();

        log.info("[DEBUG_LOG] 토큰으로 토큰 검증 테스트 중: {}", token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);

        // 실행
        boolean isValid = tokenValidator.validateToken(token);

        // 검증
        assertThat(isValid).isTrue();
        log.info("[DEBUG_LOG] 토큰 검증 결과: {}", isValid);
    }
}
