package io.github.ruifoot.domain.repository;

import io.github.ruifoot.domain.model.user.UserPositions;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for UserPositions
 */
public interface UserPositionRepository {
    /**
     * Find a user position by ID
     * @param id User position ID
     * @return Optional containing the user position if found, empty otherwise
     */
    Optional<UserPositions> findById(long id);
    
    /**
     * Find user positions by user baseball ID
     * @param userBaseballId User baseball ID
     * @return List of user positions
     */
    List<UserPositions> findByUserBaseballId(long userBaseballId);
    
    /**
     * Save a user position
     * @param userPosition User position to save
     * @return Saved user position
     */
    UserPositions save(UserPositions userPosition);
}