package io.github.ruifoot.api.dto.auth.request;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record RegisterRequest(
        @NotBlank
        String username,
        @NotBlank
        String password,
        @Email
        String email,

        @Valid
        ProfileData profile,

        @Valid
        BaseballData baseball,

        @Valid
        PositionData position
) {
    public record ProfileData(
            String fullName,
            String birthDate,
            String phone,
            String residence
    ) {}

    public record BaseballData(
            Integer teamId,
            Integer jerseyNo,
            String throwingHand,
            String battingHand
    ) {}

    public record PositionData(
            List<Integer> positionIds
    ) {}
}
