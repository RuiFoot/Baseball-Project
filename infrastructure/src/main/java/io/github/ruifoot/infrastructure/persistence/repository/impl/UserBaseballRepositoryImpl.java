package io.github.ruifoot.infrastructure.persistence.repository.impl;

import io.github.ruifoot.domain.model.user.UserBaseball;
import io.github.ruifoot.domain.repository.UserBaseballRepository;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserBaseballMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.TeamJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserBaseballJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserBaseballRepositoryImpl implements UserBaseballRepository {

    private final UserBaseballJpaRepository userBaseballJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final UserBaseballMapper userBaseballMapper;

    @Override
    public Optional<UserBaseball> findById(long id) {
        return userBaseballJpaRepository.findById((int) id)
                .map(userBaseballMapper::toDomain);
    }

    @Override
    public Optional<UserBaseball> findByUserId(long userId) {
        return userJpaRepository.findById((int) userId)
                .flatMap(userBaseballJpaRepository::findByUsers)
                .map(userBaseballMapper::toDomain);
    }

    @Override
    public UserBaseball save(UserBaseball userBaseball) {
        io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball entity = userBaseballMapper.toEntity(userBaseball);

        // If this is a new baseball info with a userId, set the Users reference
        if (entity.getId() == null && userBaseball.getUserId() > 0) {
            userJpaRepository.findById((int) userBaseball.getUserId())
                    .ifPresent(entity::setUsers);
        }

        // If teamId is provided, set the Teams reference
        if (userBaseball.getTeamId() > 0) {
            teamJpaRepository.findById(userBaseball.getTeamId())
                    .ifPresent(entity::setTeams);
        }

        io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball savedEntity = userBaseballJpaRepository.save(entity);
        return userBaseballMapper.toDomain(savedEntity);
    }
}
