package io.github.ruifoot.domain.service.auth;

import io.github.ruifoot.domain.dto.auth.request.RegisterDto;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.model.user.Users;

public interface AuthService {

    String getUsername(long id);
    /**
     * Register a new user with profile, baseball, and position information
     * @return Registered user
     */
    Users register(RegisterDto coreDto);

    /**
     * Log in a user
     * @param email Email
     * @param password Password (plain text)
     * @return TokenInfo containing access and refresh tokens
     */
    JwtToken login(String email, String password);

    /**
     * Refresh access token using refresh token
     * @param refreshToken Refresh token
     * @return TokenInfo containing new access and refresh tokens
     */
    JwtToken refreshToken(String refreshToken);

    /**
     * Log out a user by invalidating their refresh token
     *
     * @param refreshToken Refresh token to invalidate
     */
    boolean logout(String refreshToken);
}
