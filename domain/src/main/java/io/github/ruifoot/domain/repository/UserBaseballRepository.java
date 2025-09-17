package io.github.ruifoot.domain.repository;

import io.github.ruifoot.domain.model.user.UserBaseball;

import java.util.Optional;

/**
 * Repository interface for UserBaseball
 */
public interface UserBaseballRepository {
    /**
     * Find a user baseball by ID
     * @param id User baseball ID
     * @return Optional containing the user baseball if found, empty otherwise
     */
    Optional<UserBaseball> findById(long id);
    
    /**
     * Find a user baseball by user ID
     * @param userId User ID
     * @return Optional containing the user baseball if found, empty otherwise
     */
    Optional<UserBaseball> findByUserId(long userId);
    
    /**
     * Save a user baseball
     * @param userBaseball User baseball to save
     * @return Saved user baseball
     */
    UserBaseball save(UserBaseball userBaseball);
}