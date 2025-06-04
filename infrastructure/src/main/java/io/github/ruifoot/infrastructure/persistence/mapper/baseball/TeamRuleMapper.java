package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.TeamRule;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between TeamRule entity and TeamRule domain model.
 */
@Component
public class TeamRuleMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule, TeamRule> {

    @Override
    public TeamRule toDomain(io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule entity) {
        if (entity == null) {
            return null;
        }

        TeamRule domain = new TeamRule();
        domain.setId(entity.getId());
        domain.setContent(entity.getContent());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        // Avoid circular reference by not setting teams here

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule toEntity(TeamRule domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule entity = new io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setContent(domain.getContent());

        // Avoid circular reference by not setting teams here

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRule entity, TeamRule domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setContent(domain.getContent());

        // Avoid circular reference by not updating teams here

        return entity;
    }
}
