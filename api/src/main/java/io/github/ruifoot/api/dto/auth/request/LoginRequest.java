package io.github.ruifoot.api.dto.auth.request;

import jakarta.validation.constraints.Email;

public record LoginRequest(
        @Email
        String email,
        String password
) {
}
