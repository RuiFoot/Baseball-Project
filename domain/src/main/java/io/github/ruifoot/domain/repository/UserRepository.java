package io.github.ruifoot.domain.repository;

import io.github.ruifoot.domain.model.user.UserBaseball;
import io.github.ruifoot.domain.model.user.UserPositions;
import io.github.ruifoot.domain.model.user.UserProfiles;
import io.github.ruifoot.domain.model.user.Users;

import java.util.Optional;

/**
 * Repository interface for Users
 */
public interface UserRepository {
    /**
     * Find a user by ID
     * @param id User ID
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<Users> findById(long id);

    /**
     * Find a user by username
     * @param username Username
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);

    /**
     * Check if a username exists
     * @param username Username
     * @return True if the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if an email exists
     * @param email Email
     * @return True if the email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Save a user
     * @param user User to save
     * @return Saved user
     */
    Users save(Users user);

    /**
     * Save a user with profile, baseball, and position information
     * @param user User to save
     * @param profile User profile to save
     * @param baseball User baseball to save
     * @param positions User positions to save
     * @return Saved user
     */
    Users saveWithRelationships(Users user, UserProfiles profile,
                               UserBaseball baseball,
                               java.util.List<UserPositions> positions);
}
