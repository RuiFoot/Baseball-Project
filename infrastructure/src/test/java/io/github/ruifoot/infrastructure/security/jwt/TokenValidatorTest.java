package io.github.ruifoot.infrastructure.security.jwt;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.test.BaseTest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
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
        tokenValidator = new TokenValidator(jwtTokenProvider);
    }

    @Test
    void getAuthentication_ReturnsNull_WhenTokenIsInvalid() {
        // given
        String invalidToken = "invalid.token";
        log.info("[DEBUG_LOG] Testing with invalid token: {}", invalidToken);
        when(jwtTokenProvider.validateToken(invalidToken)).thenReturn(false);

        // when
        Authentication authentication = tokenValidator.getAuthentication(invalidToken);

        // then
        assertThat(authentication).isNull();
        log.info("[DEBUG_LOG] Authentication result is null as expected for invalid token");
    }

    @Test
    void getAuthentication_ReturnsAuthentication_WhenTokenIsValid() {
        // given
        String validToken = "valid.token";
        String userId = "user123";
        log.info("[DEBUG_LOG] Testing with valid token: {}", validToken);
        log.info("[DEBUG_LOG] Expected user ID: {}", userId);
        when(jwtTokenProvider.validateToken(validToken)).thenReturn(true);
        when(jwtTokenProvider.getUserIdFromToken(validToken)).thenReturn(userId);

        // when
        Authentication authentication = tokenValidator.getAuthentication(validToken);

        // then
        assertThat(authentication).isNotNull();
        assertThat(authentication.getPrincipal()).isEqualTo(userId);
        log.info("[DEBUG_LOG] Authentication result: principal={}, authorities={}", 
                authentication.getPrincipal(), 
                authentication.getAuthorities());
    }

    @Test
    void resolveToken_ReturnsToken_WhenAuthorizationHeaderIsValid() {
        // given
        String token = "valid.token";
        String authHeader = "Bearer " + token;
        log.info("[DEBUG_LOG] Testing with Authorization header: {}", authHeader);
        when(request.getHeader("Authorization")).thenReturn(authHeader);

        // when
        String resolvedToken = tokenValidator.resolveToken(request);

        // then
        assertThat(resolvedToken).isEqualTo(token);
        log.info("[DEBUG_LOG] Resolved token: {}", resolvedToken);
    }

    @Test
    void resolveToken_ReturnsNull_WhenAuthorizationHeaderIsInvalid() {
        // given
        String invalidHeader = "Invalid token";
        log.info("[DEBUG_LOG] Testing with invalid Authorization header: {}", invalidHeader);
        when(request.getHeader("Authorization")).thenReturn(invalidHeader);

        // when
        String resolvedToken = tokenValidator.resolveToken(request);

        // then
        assertThat(resolvedToken).isNull();
        log.info("[DEBUG_LOG] Resolved token is null as expected for invalid header");
    }

    @Test
    void resolveToken_ReturnsNull_WhenAuthorizationHeaderIsNull() {
        // given
        log.info("[DEBUG_LOG] Testing with null Authorization header");
        when(request.getHeader("Authorization")).thenReturn(null);

        // when
        String resolvedToken = tokenValidator.resolveToken(request);

        // then
        assertThat(resolvedToken).isNull();
        log.info("[DEBUG_LOG] Resolved token is null as expected for null header");
    }

    @Test
    void validateToken_DelegatesTo_JwtTokenProvider() {
        // given
        String token = "token";
        log.info("[DEBUG_LOG] Testing token validation with token: {}", token);
        when(jwtTokenProvider.validateToken(token)).thenReturn(true);

        // when
        boolean isValid = tokenValidator.validateToken(token);

        // then
        assertThat(isValid).isTrue();
        log.info("[DEBUG_LOG] Token validation result: {}", isValid);
    }
}
