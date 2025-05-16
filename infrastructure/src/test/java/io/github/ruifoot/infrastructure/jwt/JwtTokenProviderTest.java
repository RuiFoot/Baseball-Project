package io.github.ruifoot.infrastructure.jwt;

import io.github.ruifoot.infrastructure.security.jwt.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class JwtTokenProviderTest {

    private JwtTokenProvider jwtTokenProvider;

    private final String secret = "testsecretkeytestsecretkeytestsecretkey";

    private final long expiration = 3600 * 1000L; // 1시간 (ms 단위)

    @BeforeEach
    void setUp() {
        jwtTokenProvider = new JwtTokenProvider(secret, expiration);
    }

    @Test
    void testCreateAndValidateToken() {
        // given
        String userId = "testuser";

        // when
        String token = jwtTokenProvider.createAccessToken(userId);

        // then
        assertThat(jwtTokenProvider.validateToken(token)).isTrue();
        assertThat(jwtTokenProvider.getUserIdFromToken(token)).isEqualTo(userId);
    }

    @Test
    void getUserIdFromToken_Returns_CorrectUsername() {
        // given
        String username = "testuser";
        String token = jwtTokenProvider.createAccessToken(username);

        // when
        String extractedUsername = jwtTokenProvider.getUserIdFromToken(token);

        // then
        assertThat(extractedUsername).isEqualTo(username);
    }

    @Test
    void validateToken_ReturnsFalse_ForInvalidToken() {
        // given
        String invalidToken = "invalid.token.string";

        // when
        boolean isValid = jwtTokenProvider.validateToken(invalidToken);

        // then
        assertThat(isValid).isFalse();
    }
}
