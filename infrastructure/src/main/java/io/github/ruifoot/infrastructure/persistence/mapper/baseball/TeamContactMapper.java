package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.TeamContact;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between TeamContact entity and TeamContact domain model.
 */
@Component
public class TeamContactMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact, TeamContact> {

    @Override
    public TeamContact toDomain(io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact entity) {
        if (entity == null) {
            return null;
        }

        TeamContact domain = new TeamContact();
        domain.setId(entity.getId());
        domain.setPhoneNumber(entity.getPhoneNumber());
        domain.setEmail(entity.getEmail());
        domain.setAddress(entity.getAddress());
        domain.setImageUrl(entity.getImageUrl());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        // Avoid circular reference by not setting teams here
        // domain.setTeams(teamsMapper.toDomain(entity.getTeams()));

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact toEntity(TeamContact domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact entity = new io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setEmail(domain.getEmail());
        entity.setAddress(domain.getAddress());
        entity.setImageUrl(domain.getImageUrl());

        // Avoid circular reference by not setting teams here
        // entity.setTeams(teamsMapper.toEntity(domain.getTeams()));

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamContact entity, TeamContact domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setPhoneNumber(domain.getPhoneNumber());
        entity.setEmail(domain.getEmail());
        entity.setAddress(domain.getAddress());
        entity.setImageUrl(domain.getImageUrl());

        // Avoid circular reference by not updating teams here
        // if (domain.getTeams() != null) {
        //     entity.setTeams(teamsMapper.toEntity(domain.getTeams()));
        // }

        return entity;
    }
}
