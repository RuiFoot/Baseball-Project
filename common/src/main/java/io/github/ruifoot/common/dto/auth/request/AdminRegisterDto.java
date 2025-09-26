package io.github.ruifoot.common.dto.auth.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Domain DTO for admin user registration
 */
public record AdminRegisterDto(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @Email
        String email,

        @Valid
        RegisterDto.ProfileData profile,

        @Valid
        RegisterDto.BaseballData baseball,

        @Valid
        RegisterDto.PositionData position
) {}