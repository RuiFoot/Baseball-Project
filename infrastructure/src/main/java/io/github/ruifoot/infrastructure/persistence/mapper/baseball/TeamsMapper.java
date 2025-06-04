package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.Teams;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Mapper for converting between Teams entity and Teams domain model.
 */
@Component
public class TeamsMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams, Teams> {

    private final TeamContactMapper teamContactMapper;
    private final TeamInfoMapper teamInfoMapper;

    public TeamsMapper(@Lazy TeamContactMapper teamContactMapper, @Lazy TeamInfoMapper teamInfoMapper) {
        this.teamContactMapper = teamContactMapper;
        this.teamInfoMapper = teamInfoMapper;
    }

    @Override
    public Teams toDomain(io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams entity) {
        if (entity == null) {
            return null;
        }

        Teams domain = new Teams();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        domain.setName(entity.getName());
        domain.setFoundedDate(entity.getFoundedDate());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getContact() != null) {
            domain.setContact(teamContactMapper.toDomain(entity.getContact()));
        }
        
        if (entity.getTeamInfo() != null) {
            domain.setTeamInfo(teamInfoMapper.toDomain(entity.getTeamInfo()));
        }
        
        // Histories and rules are not directly accessible from the entity
        // They would need to be loaded separately and set here
        domain.setHistories(new ArrayList<>());
        domain.setRules(new ArrayList<>());

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams toEntity(Teams domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams entity = new io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams();
        
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        entity.setName(domain.getName());
        entity.setFoundedDate(domain.getFoundedDate());
        
        if (domain.getContact() != null) {
            entity.setContact(teamContactMapper.toEntity(domain.getContact()));
            entity.getContact().setTeams(entity); // Set the back reference
        }
        
        if (domain.getTeamInfo() != null) {
            entity.setTeamInfo(teamInfoMapper.toEntity(domain.getTeamInfo()));
            entity.getTeamInfo().setTeams(entity); // Set the back reference
        }
        
        // Histories and rules are not directly set on the entity
        // They would need to be saved separately

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams entity, Teams domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setName(domain.getName());
        entity.setFoundedDate(domain.getFoundedDate());
        
        if (domain.getContact() != null && entity.getContact() != null) {
            teamContactMapper.updateEntityFromDomain(entity.getContact(), domain.getContact());
        } else if (domain.getContact() != null) {
            entity.setContact(teamContactMapper.toEntity(domain.getContact()));
            entity.getContact().setTeams(entity); // Set the back reference
        }
        
        if (domain.getTeamInfo() != null && entity.getTeamInfo() != null) {
            teamInfoMapper.updateEntityFromDomain(entity.getTeamInfo(), domain.getTeamInfo());
        } else if (domain.getTeamInfo() != null) {
            entity.setTeamInfo(teamInfoMapper.toEntity(domain.getTeamInfo()));
            entity.getTeamInfo().setTeams(entity); // Set the back reference
        }
        
        // Histories and rules are not directly updated on the entity
        // They would need to be updated separately

        return entity;
    }
}