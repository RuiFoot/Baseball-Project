package io.github.ruifoot.domain.dto.auth.request;

/**
 * Domain DTO for admin user registration
 */
public record AdminRegisterDto(
        String username,
        String password,
        String email,
        RegisterDto.ProfileData profile,
        RegisterDto.BaseballData baseball,
        RegisterDto.PositionData position
) {}