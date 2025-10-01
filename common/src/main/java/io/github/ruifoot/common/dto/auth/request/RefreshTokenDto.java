package io.github.ruifoot.common.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "리프레시 토큰 요청 DTO")
public record RefreshTokenDto(
    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9...")
    @NotBlank(message = "Refresh token is required")
    String refreshToken
) {}