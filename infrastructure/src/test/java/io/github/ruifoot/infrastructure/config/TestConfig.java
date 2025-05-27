package io.github.ruifoot.infrastructure.config;

import io.github.ruifoot.infrastructure.security.jwt.JwtTokenProvider;
import io.github.ruifoot.infrastructure.security.jwt.TokenValidator;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public TokenValidator tokenValidator() {
        return Mockito.mock(TokenValidator.class);
    }

    @Bean
    @Primary
    public JwtTokenProvider jwtTokenProvider() {
        return Mockito.mock(JwtTokenProvider.class);
    }
}