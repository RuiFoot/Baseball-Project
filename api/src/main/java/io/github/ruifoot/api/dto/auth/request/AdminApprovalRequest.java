package io.github.ruifoot.api.dto.auth.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for admin approval
 */
public record AdminApprovalRequest(
        @NotBlank
        String username,
        
        boolean approved
) {}