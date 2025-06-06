package io.github.ruifoot.infrastructure.config;

import io.github.ruifoot.infrastructure.security.jwt.JwtTokenProvider;
import io.github.ruifoot.infrastructure.security.jwt.TokenValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Configuration
public class TestConfig {

    private static final String TEST_TOKEN = "test.jwt.token";

    @Bean
    @Primary
    public TokenValidator tokenValidator() {
        TokenValidator tokenValidator = Mockito.mock(TokenValidator.class);

        // Configure mock to validate test token
        when(tokenValidator.validateToken(TEST_TOKEN)).thenReturn(true);

        // Configure mock to extract token from Authorization header
        when(tokenValidator.resolveToken(Mockito.any())).thenAnswer(invocation -> {
            var request = invocation.getArgument(0);
            try {
                String authHeader = (String) request.getClass().getMethod("getHeader", String.class)
                        .invoke(request, "Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    return authHeader.substring(7);
                }
            } catch (Exception e) {
                // Ignore
            }
            return null;
        });

        // Configure mock to create authentication from token
        when(tokenValidator.getAuthentication(anyString())).thenAnswer(invocation -> {
            String token = invocation.getArgument(0);
            if (TEST_TOKEN.equals(token)) {
                return new UsernamePasswordAuthenticationToken(
                        "user",
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
            return null;
        });

        return tokenValidator;
    }

    @Bean
    @Primary
    public JwtTokenProvider jwtTokenProvider() {
        JwtTokenProvider jwtTokenProvider = Mockito.mock(JwtTokenProvider.class);

        // Configure mock to validate test token
        when(jwtTokenProvider.validateToken(TEST_TOKEN)).thenReturn(true);

        // Configure mock to extract user ID from token
        when(jwtTokenProvider.getUserIdFromToken(TEST_TOKEN)).thenReturn("user");

        return jwtTokenProvider;
    }

    public static String getTestToken() {
        return TEST_TOKEN;
    }
}
