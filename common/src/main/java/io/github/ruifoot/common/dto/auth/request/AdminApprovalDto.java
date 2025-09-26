package io.github.ruifoot.common.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Domain DTO for admin approval
 */
public record AdminApprovalDto(
        @NotBlank
        String username,
        boolean approved
) {}