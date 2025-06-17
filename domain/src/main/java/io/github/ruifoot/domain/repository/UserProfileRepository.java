package io.github.ruifoot.domain.repository;

import io.github.ruifoot.domain.model.user.UserProfiles;

import java.util.Optional;

/**
 * Repository interface for UserProfiles
 */
public interface UserProfileRepository {
    /**
     * Find a user profile by ID
     * @param id User profile ID
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfiles> findById(long id);
    
    /**
     * Find a user profile by user ID
     * @param userId User ID
     * @return Optional containing the user profile if found, empty otherwise
     */
    Optional<UserProfiles> findByUserId(long userId);
    
    /**
     * Save a user profile
     * @param userProfile User profile to save
     * @return Saved user profile
     */
    UserProfiles save(UserProfiles userProfile);
}