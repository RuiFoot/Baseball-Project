package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.UserPositions;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.PositionsEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseballEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserPositionEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserPosition entity and UserPositions domain model.
 */
@Component
public class UserPositionMapper implements EntityMapper<UserPositionEntity, UserPositions> {

    @Override
    public UserPositions toDomain(UserPositionEntity entity) {
        if (entity == null) {
            return null;
        }

        UserPositions domain = new UserPositions();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        
        // Map UserBaseball entity to userBaseballId
        if (entity.getUserBaseball() != null && entity.getUserBaseball().getId() != null) {
            domain.setUserBaseballId(entity.getUserBaseball().getId());
        }
        
        // Map Position entity to positionId
        if (entity.getPositions() != null && entity.getPositions().getId() != null) {
            domain.setPositionId(entity.getPositions().getId());
        }

        return domain;
    }

    @Override
    public UserPositionEntity toEntity(UserPositions domain) {
        if (domain == null) {
            return null;
        }

        UserPositionEntity entity = new UserPositionEntity();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        // For UserBaseball reference, we only set the ID
        // The actual UserBaseball object should be loaded by the repository
        if (domain.getUserBaseballId() > 0) {
            UserBaseballEntity userBaseballEntity = new UserBaseballEntity();
            userBaseballEntity.setId((int) domain.getUserBaseballId());
            entity.setUserBaseball(userBaseballEntity);
        }
        
        // For Position reference, we only set the ID
        // The actual Position object should be loaded by the repository
        if (domain.getPositionId() > 0) {
            PositionsEntity positionsEntity = new PositionsEntity();
            positionsEntity.setId((int) domain.getPositionId());
            entity.setPositions(positionsEntity);
        }

        return entity;
    }

    @Override
    public UserPositionEntity updateEntityFromDomain(UserPositionEntity entity, UserPositions domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        // For UserBaseball reference, we only update if the userBaseballId has changed
        if (domain.getUserBaseballId() > 0 && 
            (entity.getUserBaseball() == null || entity.getUserBaseball().getId() != domain.getUserBaseballId())) {
            UserBaseballEntity userBaseballEntity = new UserBaseballEntity();
            userBaseballEntity.setId((int) domain.getUserBaseballId());
            entity.setUserBaseball(userBaseballEntity);
        }
        
        // For Position reference, we only update if the positionId has changed
        if (domain.getPositionId() > 0 && 
            (entity.getPositions() == null || entity.getPositions().getId() != domain.getPositionId())) {
            PositionsEntity positionsEntity = new PositionsEntity();
            positionsEntity.setId((int) domain.getPositionId());
            entity.setPositions(positionsEntity);
        }

        return entity;
    }
}