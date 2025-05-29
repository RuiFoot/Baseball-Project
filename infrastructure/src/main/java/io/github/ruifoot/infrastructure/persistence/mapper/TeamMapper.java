package io.github.ruifoot.infrastructure.persistence.mapper;

import io.github.ruifoot.domain.model.Teams;
import io.github.ruifoot.infrastructure.persistence.entity.Team;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;


/**
 * Mapper for converting between Team entity and Teams domain model.
 */
@Component
public class TeamMapper implements EntityMapper<Team, Teams> {

    @Override
    public Teams toDomain(Team entity) {
        if (entity == null) {
            return null;
        }

        Teams domain = new Teams();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        domain.setName(entity.getName());
        
        // Convert LocalDate to Date
        if (entity.getFoundedDate() != null) {
            domain.setFoundedDate(Date.from(entity.getFoundedDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
        
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    @Override
    public Team toEntity(Teams domain) {
        if (domain == null) {
            return null;
        }

        Team entity = new Team();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        entity.setName(domain.getName());
        
        // Convert Date to LocalDate
        if (domain.getFoundedDate() != null) {
            entity.setFoundedDate(domain.getFoundedDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        }
        
        // Note: createdAt and updatedAt are managed by JPA/Hibernate

        return entity;
    }

    @Override
    public Team updateEntityFromDomain(Team entity, Teams domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setName(domain.getName());
        
        // Convert Date to LocalDate
        if (domain.getFoundedDate() != null) {
            entity.setFoundedDate(domain.getFoundedDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate());
        } else {
            entity.setFoundedDate(null);
        }
        
        // Note: updatedAt will be automatically updated by JPA/Hibernate

        return entity;
    }
}