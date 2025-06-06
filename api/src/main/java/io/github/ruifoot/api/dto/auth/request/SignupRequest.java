package io.github.ruifoot.api.dto.auth.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignupRequest(

        @NotBlank
        String username,
        @NotBlank
        String password,
        @Email
        String email
) {
}
