package io.github.ruifoot.common.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Domain DTO for admin approval
 */
@Schema(description = "관리자 승인 요청 DTO")
public record AdminApprovalDto(
        @Schema(description = "사용자 이름", example = "testuser")
        @NotBlank
        String username,
        @Schema(description = "승인 여부", example = "true")
        boolean approved
) {}