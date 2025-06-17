package io.github.ruifoot.domain.dto.auth.request;

import java.util.List;

public record RegisterDto(
        String username,
        String password,
        String email,
        ProfileData profile,
        BaseballData baseball,
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