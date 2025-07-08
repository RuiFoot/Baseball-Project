package io.github.ruifoot.api.mapper;

import io.github.ruifoot.api.dto.auth.request.AdminApprovalRequest;
import io.github.ruifoot.api.dto.auth.request.AdminRegisterRequest;
import io.github.ruifoot.api.dto.auth.request.RegisterRequest;
import io.github.ruifoot.domain.dto.auth.request.AdminApprovalDto;
import io.github.ruifoot.domain.dto.auth.request.AdminRegisterDto;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto.ProfileData;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto.BaseballData;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto.PositionData;

/**
 * Mapper class to convert API requests to Core DTOs
 */
public class RegisterMapper {

    /**
     * Convert API RegisterRequest to Core RegisterDto
     * @param apiRequest API RegisterRequest
     * @return Core RegisterDto
     */
    public static RegisterDto toCore(RegisterRequest apiRequest) {
        if (apiRequest == null) {
            return null;
        }

        // Map profile data
        ProfileData profileData = null;
        if (apiRequest.profile() != null) {
            profileData = new ProfileData(
                apiRequest.profile().fullName(),
                apiRequest.profile().birthDate(),
                apiRequest.profile().phone(),
                apiRequest.profile().residence()
            );
        }

        // Map baseball data
        BaseballData baseballData = null;
        if (apiRequest.baseball() != null) {
            baseballData = new BaseballData(
                apiRequest.baseball().teamId(),
                apiRequest.baseball().jerseyNo(),
                apiRequest.baseball().throwingHand(),
                apiRequest.baseball().battingHand()
            );
        }

        // Map position data
        PositionData positionData = null;
        if (apiRequest.position() != null) {
            positionData = new PositionData(
                apiRequest.position().positionIds()
            );
        }

        // Create and return core SignupRequest
        return new RegisterDto(
            apiRequest.username(),
            apiRequest.password(),
            apiRequest.email(),
            profileData,
            baseballData,
            positionData
        );
    }

    /**
     * Convert API AdminRegisterRequest to Core AdminRegisterDto
     * @param apiRequest API AdminRegisterRequest
     * @return Core AdminRegisterDto
     */
    public static AdminRegisterDto toCoreAdmin(AdminRegisterRequest apiRequest) {
        if (apiRequest == null) {
            return null;
        }

        // Map profile data
        ProfileData profileData = null;
        if (apiRequest.profile() != null) {
            profileData = new ProfileData(
                apiRequest.profile().fullName(),
                apiRequest.profile().birthDate(),
                apiRequest.profile().phone(),
                apiRequest.profile().residence()
            );
        }

        // Map baseball data
        BaseballData baseballData = null;
        if (apiRequest.baseball() != null) {
            baseballData = new BaseballData(
                apiRequest.baseball().teamId(),
                apiRequest.baseball().jerseyNo(),
                apiRequest.baseball().throwingHand(),
                apiRequest.baseball().battingHand()
            );
        }

        // Map position data
        PositionData positionData = null;
        if (apiRequest.position() != null) {
            positionData = new PositionData(
                apiRequest.position().positionIds()
            );
        }

        // Create and return core AdminRegisterDto
        return new AdminRegisterDto(
            apiRequest.username(),
            apiRequest.password(),
            apiRequest.email(),
            profileData,
            baseballData,
            positionData
        );
    }

    /**
     * Convert API AdminApprovalRequest to Core AdminApprovalDto
     * @param apiRequest API AdminApprovalRequest
     * @return Core AdminApprovalDto
     */
    public static AdminApprovalDto toCoreApproval(AdminApprovalRequest apiRequest) {
        if (apiRequest == null) {
            return null;
        }

        return new AdminApprovalDto(
            apiRequest.username(),
            apiRequest.approved()
        );
    }
}
