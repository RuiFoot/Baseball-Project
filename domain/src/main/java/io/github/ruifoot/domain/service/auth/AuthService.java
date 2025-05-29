package io.github.ruifoot.domain.service.auth;

import io.github.ruifoot.domain.model.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;

public interface AuthService {

    String getUsername(long id);
    /**
     * Register a new user
     * @param username Username
     * @param email Email
     * @param password Password (plain text)
     * @return Registered user
     */
    Users register(String username, String email, String password);

    /**
     * Login a user
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
     * Logout a user by invalidating their refresh token
     * @param refreshToken Refresh token to invalidate
     */
    boolean logout(String refreshToken);
}
