package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.TeamHistory;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamHistoryEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between TeamHistory entity and TeamHistory domain model.
 */
@Component
public class TeamHistoryMapper implements EntityMapper<TeamHistoryEntity, TeamHistory> {

    @Override
    public TeamHistory toDomain(TeamHistoryEntity entity) {
        if (entity == null) {
            return null;
        }

        TeamHistory domain = new TeamHistory();
        domain.setId(entity.getId());
        domain.setDescription(entity.getDescription());
        domain.setDate(entity.getDate());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        // Avoid circular reference by not setting teams here

        return domain;
    }

    @Override
    public TeamHistoryEntity toEntity(TeamHistory domain) {
        if (domain == null) {
            return null;
        }

        TeamHistoryEntity entity = new TeamHistoryEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setDescription(domain.getDescription());
        entity.setDate(domain.getDate());

        // Avoid circular reference by not setting teams here

        return entity;
    }

    @Override
    public TeamHistoryEntity updateEntityFromDomain(
            TeamHistoryEntity entity, TeamHistory domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setDescription(domain.getDescription());
        entity.setDate(domain.getDate());

        // Avoid circular reference by not updating teams here

        return entity;
    }
}
