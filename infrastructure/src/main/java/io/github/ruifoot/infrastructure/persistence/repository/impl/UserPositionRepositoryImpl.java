package io.github.ruifoot.infrastructure.persistence.repository.impl;

import io.github.ruifoot.domain.model.user.UserPositions;
import io.github.ruifoot.domain.repository.UserPositionRepository;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserPosition;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserPositionMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.PositionsJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserBaseballJpaRepository;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserPositionJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class UserPositionRepositoryImpl implements UserPositionRepository {

    private final UserPositionJpaRepository userPositionJpaRepository;
    private final UserBaseballJpaRepository userBaseballJpaRepository;
    private final PositionsJpaRepository positionsJpaRepository;
    private final UserPositionMapper userPositionMapper;

    @Override
    public Optional<UserPositions> findById(long id) {
        return userPositionJpaRepository.findById((int) id)
                .map(userPositionMapper::toDomain);
    }

    @Override
    public List<UserPositions> findByUserBaseballId(long userBaseballId) {
        return userBaseballJpaRepository.findById((int) userBaseballId)
                .map(userPositionJpaRepository::findByUserBaseball)
                .orElse(List.of())
                .stream()
                .map(userPositionMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public UserPositions save(UserPositions userPosition) {
        UserPosition entity = userPositionMapper.toEntity(userPosition);

        // If this is a new position with a userBaseballId, set the UserBaseball reference
        if (entity.getId() == null && userPosition.getUserBaseballId() > 0) {
            userBaseballJpaRepository.findById((int) userPosition.getUserBaseballId())
                    .ifPresent(entity::setUserBaseball);
        }

        // If positionId is provided, set the Positions reference
        if (userPosition.getPositionId() > 0) {
            positionsJpaRepository.findById((int) userPosition.getPositionId())
                    .ifPresent(entity::setPositions);
        }

        UserPosition savedEntity = userPositionJpaRepository.save(entity);
        return userPositionMapper.toDomain(savedEntity);
    }
}
