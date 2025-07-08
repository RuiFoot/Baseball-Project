package io.github.ruifoot.api.dto.auth.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Request DTO for admin user registration
 */
public record AdminRegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @Email
        String email,

        @Valid
        RegisterRequest.ProfileData profile,

        @Valid
        RegisterRequest.BaseballData baseball,

        @Valid
        RegisterRequest.PositionData position
) {}