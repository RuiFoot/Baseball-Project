package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.UserPositions;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Positions;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserPosition;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserPosition entity and UserPositions domain model.
 */
@Component
public class UserPositionMapper implements EntityMapper<UserPosition, UserPositions> {

    @Override
    public UserPositions toDomain(UserPosition entity) {
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
    public UserPosition toEntity(UserPositions domain) {
        if (domain == null) {
            return null;
        }

        UserPosition entity = new UserPosition();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        // For UserBaseball reference, we only set the ID
        // The actual UserBaseball object should be loaded by the repository
        if (domain.getUserBaseballId() > 0) {
            UserBaseball userBaseball = new UserBaseball();
            userBaseball.setId((int) domain.getUserBaseballId());
            entity.setUserBaseball(userBaseball);
        }
        
        // For Position reference, we only set the ID
        // The actual Position object should be loaded by the repository
        if (domain.getPositionId() > 0) {
            Positions positions = new Positions();
            positions.setId((int) domain.getPositionId());
            entity.setPositions(positions);
        }

        return entity;
    }

    @Override
    public UserPosition updateEntityFromDomain(UserPosition entity, UserPositions domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        // For UserBaseball reference, we only update if the userBaseballId has changed
        if (domain.getUserBaseballId() > 0 && 
            (entity.getUserBaseball() == null || entity.getUserBaseball().getId() != domain.getUserBaseballId())) {
            UserBaseball userBaseball = new UserBaseball();
            userBaseball.setId((int) domain.getUserBaseballId());
            entity.setUserBaseball(userBaseball);
        }
        
        // For Position reference, we only update if the positionId has changed
        if (domain.getPositionId() > 0 && 
            (entity.getPositions() == null || entity.getPositions().getId() != domain.getPositionId())) {
            Positions positions = new Positions();
            positions.setId((int) domain.getPositionId());
            entity.setPositions(positions);
        }

        return entity;
    }
}