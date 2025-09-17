package io.github.ruifoot.infrastructure.persistence.repository.impl;

import io.github.ruifoot.domain.model.user.UserBaseball;
import io.github.ruifoot.domain.model.user.UserPositions;
import io.github.ruifoot.domain.model.user.UserProfiles;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Positions;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserPosition;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserProfile;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.TeamJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<io.github.ruifoot.domain.model.user.Users> findById(long id) {
        return userJpaRepository.findById((int) id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<io.github.ruifoot.domain.model.user.Users> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<io.github.ruifoot.domain.model.user.Users> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public io.github.ruifoot.domain.model.user.Users save(io.github.ruifoot.domain.model.user.Users users) {
        Users entity = userMapper.toEntity(users);

        // If this is an existing user, load the existing entity to preserve relationships
        if (users.getId() > 0) {
            userJpaRepository.findById((int) users.getId())
                .ifPresent(existingEntity -> {
                    // Preserve the profile and baseball relationships
                    entity.setProfile(existingEntity.getProfile());
                    entity.setBaseball(existingEntity.getBaseball());
                });
        }

        Users savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public io.github.ruifoot.domain.model.user.Users saveWithRelationships(
            io.github.ruifoot.domain.model.user.Users user,
            UserProfiles profile,
            UserBaseball baseball,
            List<UserPositions> positions) {

        // Convert user domain model to entity
        Users userEntity = userMapper.toEntity(user);

        // If profile is provided, set up the relationship
        if (profile != null) {
            // Convert profile domain model to entity
            UserProfile profileEntity = new UserProfile();
            profileEntity.setFullName(profile.getFullName());
            if (profile.getBirthDate() != null) {
                profileEntity.setBirthDate(profile.getBirthDate().toLocalDate());
            }
            profileEntity.setPhone(profile.getPhone());
            profileEntity.setResidence(profile.getResidence());

            // Set up bidirectional relationship
            profileEntity.setUsers(userEntity);
            userEntity.setProfile(profileEntity);
        }

        // If baseball is provided, set up the relationship
        if (baseball != null) {
            // Convert baseball domain model to entity
            io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball baseballEntity = 
                new io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball();
            if (baseball.getTeamId() > 0) {
                Teams teamEntity = teamJpaRepository.findById(baseball.getTeamId())
                        .orElseThrow(() -> new IllegalArgumentException("팀이 존재하지 않습니다: id = " + baseball.getTeamId()));
                baseballEntity.setTeams(teamEntity);
            }
            if (baseball.getJerseyNo() > 0) {
                baseballEntity.setJerseyNo((int) baseball.getJerseyNo());
            }
            baseballEntity.setThrowingHand(baseball.getThrowingHand());
            baseballEntity.setBattingHand(baseball.getBattingHand());

            // Set up bidirectional relationship
            baseballEntity.setUsers(userEntity);
            userEntity.setBaseball(baseballEntity);

            // If positions are provided, set up the relationships
            if (positions != null && !positions.isEmpty()) {
                for (io.github.ruifoot.domain.model.user.UserPositions position : positions) {
                    // Convert position domain model to entity
                    UserPosition positionEntity = new UserPosition();

                    // Set up position reference
                    Positions positionRefEntity = new Positions();
                    positionRefEntity.setId((int) position.getPositionId());
                    positionEntity.setPositions(positionRefEntity);

                    // Set up bidirectional relationship
                    positionEntity.setUserBaseball(baseballEntity);
                    baseballEntity.getPositions().add(positionEntity);
                }
            }

        }

        // Save the user entity with all relationships
        Users savedEntity = userJpaRepository.save(userEntity);

        // Convert the saved entity back to a domain model
        return userMapper.toDomain(savedEntity);
    }
}
