package io.github.ruifoot.common.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

@Schema(description = "로그인 요청 DTO")
public record LoginDto(
        @Schema(description = "이메일", example = "test@example.com")
        @Email
        String email,
        @Schema(description = "비밀번호", example = "password123")
        String password
) {
}
