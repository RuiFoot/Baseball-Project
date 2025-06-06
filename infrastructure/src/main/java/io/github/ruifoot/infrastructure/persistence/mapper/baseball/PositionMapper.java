package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.enums.PositionCategory;
import io.github.ruifoot.domain.model.baseball.enums.PositionCode;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Positions;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Position entity and Positions domain model.
 */
@Component
public class PositionMapper implements EntityMapper<Positions, io.github.ruifoot.domain.model.baseball.Positions> {

    @Override
    public io.github.ruifoot.domain.model.baseball.Positions toDomain(Positions entity) {
        if (entity == null) {
            return null;
        }

        io.github.ruifoot.domain.model.baseball.Positions domain = new io.github.ruifoot.domain.model.baseball.Positions();
        domain.setId(entity.getId() != null ? entity.getId() : 0);

        // Convert entity enum to domain enum
        if (entity.getCode() != null) {
            domain.setCode(PositionCode.valueOf(entity.getCode().name()));
        }

        domain.setNameKr(entity.getNameKr());
        domain.setNameEn(entity.getNameEn());

        // Convert entity enum to domain enum
        if (entity.getCategory() != null) {
            domain.setCategory(PositionCategory.valueOf(entity.getCategory().name()));
        }

        return domain;
    }

    @Override
    public Positions toEntity(io.github.ruifoot.domain.model.baseball.Positions domain) {
        if (domain == null) {
            return null;
        }

        Positions entity = new Positions();

        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }

        // Convert domain enum to entity enum
        if (domain.getCode() != null) {
            entity.setCode(io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCode.valueOf(domain.getCode().name()));
        }

        entity.setNameKr(domain.getNameKr());
        entity.setNameEn(domain.getNameEn());

        // Convert domain enum to entity enum
        if (domain.getCategory() != null) {
            entity.setCategory(io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCategory.valueOf(domain.getCategory().name()));
        }

        return entity;
    }

    @Override
    public Positions updateEntityFromDomain(Positions entity, io.github.ruifoot.domain.model.baseball.Positions domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key

        // Convert domain enum to entity enum
        if (domain.getCode() != null) {
            entity.setCode(io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCode.valueOf(domain.getCode().name()));
        } else {
            entity.setCode(null);
        }

        entity.setNameKr(domain.getNameKr());
        entity.setNameEn(domain.getNameEn());

        // Convert domain enum to entity enum
        if (domain.getCategory() != null) {
            entity.setCategory(io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCategory.valueOf(domain.getCategory().name()));
        } else {
            entity.setCategory(null);
        }

        return entity;
    }
}
