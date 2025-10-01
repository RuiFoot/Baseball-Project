package io.github.ruifoot.common.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Domain DTO for admin user registration
 */
@Schema(description = "관리자 회원가입 요청 DTO")
public record AdminRegisterDto(
        @Schema(description = "사용자 이름", example = "adminuser")
        @NotBlank
        String username,
        @Schema(description = "비밀번호", example = "password123")
        @NotBlank
        String password,
        @Schema(description = "이메일", example = "admin@example.com")
        @Email
        String email,

        @Valid
        RegisterDto.ProfileData profile,

        @Valid
        RegisterDto.BaseballData baseball,

        @Valid
        RegisterDto.PositionData position
) {}