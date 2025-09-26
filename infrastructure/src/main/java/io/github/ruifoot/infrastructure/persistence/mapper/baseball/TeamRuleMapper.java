package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.TeamRule;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamRuleEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between TeamRule entity and TeamRule domain model.
 */
@Component
public class TeamRuleMapper implements EntityMapper<TeamRuleEntity, TeamRule> {

    @Override
    public TeamRule toDomain(TeamRuleEntity entity) {
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
    public TeamRuleEntity toEntity(TeamRule domain) {
        if (domain == null) {
            return null;
        }

        TeamRuleEntity entity = new TeamRuleEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setContent(domain.getContent());

        // Avoid circular reference by not setting teams here

        return entity;
    }

    @Override
    public TeamRuleEntity updateEntityFromDomain(
            TeamRuleEntity entity, TeamRule domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setContent(domain.getContent());

        // Avoid circular reference by not updating teams here

        return entity;
    }
}
