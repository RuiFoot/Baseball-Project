package io.github.ruifoot.api.dto.auth.request;

public record LoginRequest(
        String username,
        String password
) {
}
