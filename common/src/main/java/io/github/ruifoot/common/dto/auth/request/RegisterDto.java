package io.github.ruifoot.common.dto.auth.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "회원가입 요청 DTO")
public record RegisterDto(
        @Schema(description = "사용자 이름", example = "testuser")
        @NotBlank
        String username,
        @Schema(description = "비밀번호", example = "password123")
        @NotBlank
        String password,
        @Schema(description = "이메일", example = "test@example.com")
        @Email
        String email,

        @Valid
        ProfileData profile,

        @Valid
        BaseballData baseball,

        @Valid
        PositionData position
) {
    @Schema(description = "프로필 정보")
    public record ProfileData(
            @Schema(description = "전체 이름", example = "홍길동")
            String fullName,
            @Schema(description = "생년월일", example = "1990-01-01")
            String birthDate,
            @Schema(description = "전화번호", example = "010-1234-5678")
            String phone,
            @Schema(description = "거주지", example = "서울")
            String residence
    ) {}

    @Schema(description = "야구 관련 정보")
    public record BaseballData(
            @Schema(description = "팀 ID", example = "1")
            Integer teamId,
            @Schema(description = "등번호", example = "27")
            Integer jerseyNo,
            @Schema(description = "투구 손", example = "우투")
            String throwingHand,
            @Schema(description = "타격 손", example = "우타")
            String battingHand
    ) {}

    @Schema(description = "포지션 정보")
    public record PositionData(
            @Schema(description = "포지션 ID 목록", example = "[1, 2]")
            List<Integer> positionIds
    ) {}
}
