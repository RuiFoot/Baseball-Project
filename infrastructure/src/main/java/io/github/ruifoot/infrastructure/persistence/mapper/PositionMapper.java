package io.github.ruifoot.infrastructure.persistence.mapper;

import io.github.ruifoot.domain.model.Positions;
import io.github.ruifoot.infrastructure.persistence.entity.Position;
import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCategory;
import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCode;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Position entity and Positions domain model.
 */
@Component
public class PositionMapper implements EntityMapper<Position, Positions> {

    @Override
    public Positions toDomain(Position entity) {
        if (entity == null) {
            return null;
        }

        Positions domain = new Positions();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        domain.setNameKr(entity.getNameKr());
        domain.setNameEn(entity.getNameEn());
        
        // Convert enum to string
        if (entity.getCode() != null) {
            domain.setCode(entity.getCode().name());
        }
        
        if (entity.getCategory() != null) {
            domain.setCategory(entity.getCategory().name());
        }

        return domain;
    }

    @Override
    public Position toEntity(Positions domain) {
        if (domain == null) {
            return null;
        }

        Position entity = new Position();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        entity.setNameKr(domain.getNameKr());
        entity.setNameEn(domain.getNameEn());
        
        // Convert string to enum
        if (domain.getCode() != null && !domain.getCode().isEmpty()) {
            try {
                entity.setCode(PositionCode.valueOf(domain.getCode()));
            } catch (IllegalArgumentException e) {
                // Handle invalid enum value
                // Could log a warning here
            }
        }
        
        if (domain.getCategory() != null && !domain.getCategory().isEmpty()) {
            try {
                entity.setCategory(PositionCategory.valueOf(domain.getCategory()));
            } catch (IllegalArgumentException e) {
                // Handle invalid enum value
                // Could log a warning here
            }
        }

        return entity;
    }

    @Override
    public Position updateEntityFromDomain(Position entity, Positions domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setNameKr(domain.getNameKr());
        entity.setNameEn(domain.getNameEn());
        
        // Convert string to enum
        if (domain.getCode() != null && !domain.getCode().isEmpty()) {
            try {
                entity.setCode(PositionCode.valueOf(domain.getCode()));
            } catch (IllegalArgumentException e) {
                // Handle invalid enum value
                // Could log a warning here
            }
        } else {
            entity.setCode(null);
        }
        
        if (domain.getCategory() != null && !domain.getCategory().isEmpty()) {
            try {
                entity.setCategory(PositionCategory.valueOf(domain.getCategory()));
            } catch (IllegalArgumentException e) {
                // Handle invalid enum value
                // Could log a warning here
            }
        } else {
            entity.setCategory(null);
        }

        return entity;
    }
}