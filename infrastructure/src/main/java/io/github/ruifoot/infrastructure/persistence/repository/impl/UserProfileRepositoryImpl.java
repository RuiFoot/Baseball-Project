package io.github.ruifoot.infrastructure.persistence.repository.impl;

import io.github.ruifoot.domain.model.user.UserProfiles;
import io.github.ruifoot.domain.repository.UserProfileRepository;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserProfile;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserProfileMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserProfileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserProfileRepositoryImpl implements UserProfileRepository {

    private final UserProfileJpaRepository userProfileJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    public Optional<UserProfiles> findById(long id) {
        return userProfileJpaRepository.findById((int) id)
                .map(userProfileMapper::toDomain);
    }

    @Override
    public Optional<UserProfiles> findByUserId(long userId) {
        return userJpaRepository.findById((int) userId)
                .flatMap(userProfileJpaRepository::findByUsers)
                .map(userProfileMapper::toDomain);
    }

    @Override
    public UserProfiles save(UserProfiles userProfile) {
        UserProfile entity = userProfileMapper.toEntity(userProfile);
        
        // If this is a new profile with a userId, set the Users reference
        if (entity.getId() == null && userProfile.getUserId() > 0) {
            userJpaRepository.findById((int) userProfile.getUserId())
                    .ifPresent(entity::setUsers);
        }
        
        UserProfile savedEntity = userProfileJpaRepository.save(entity);
        return userProfileMapper.toDomain(savedEntity);
    }
}