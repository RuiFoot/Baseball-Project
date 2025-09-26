package io.github.ruifoot.common.dto.auth.request;

import jakarta.validation.constraints.Email;

public record LoginDto(
        @Email
        String email,
        String password
) {
}
