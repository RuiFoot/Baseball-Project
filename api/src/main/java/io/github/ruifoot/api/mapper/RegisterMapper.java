package io.github.ruifoot.api.mapper;

import io.github.ruifoot.api.dto.auth.request.RegisterRequest;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto.ProfileData;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto.BaseballData;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto.PositionData;

/**
 * Mapper class to convert API SignupRequest to Core SignupRequest
 */
public class RegisterMapper {

    /**
     * Convert API SignupRequest to Core SignupRequest
     * @param apiRequest API SignupRequest
     * @return Core SignupRequest
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
}