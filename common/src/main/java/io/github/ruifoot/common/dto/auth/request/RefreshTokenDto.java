package io.github.ruifoot.common.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenDto(
    @NotBlank(message = "Refresh token is required")
    String refreshToken
) {}