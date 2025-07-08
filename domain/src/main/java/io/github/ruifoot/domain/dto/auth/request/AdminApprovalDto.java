package io.github.ruifoot.domain.dto.auth.request;

/**
 * Domain DTO for admin approval
 */
public record AdminApprovalDto(
        String username,
        boolean approved
) {}