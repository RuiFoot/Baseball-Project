package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.Tag;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;

/**
 * Mapper for converting between Tag entity and Tag domain model.
 */
@Component
public class TagMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.notice.Tag, Tag> {

    @Override
    public Tag toDomain(io.github.ruifoot.infrastructure.persistence.entity.notice.Tag entity) {
        if (entity == null) {
            return null;
        }

        Tag domain = new Tag();
        domain.setId(entity.getId());
        domain.setName(entity.getName());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        // We don't map notices to avoid circular references
        // This will be handled by the NoticeMapper

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.Tag toEntity(Tag domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.notice.Tag entity = new io.github.ruifoot.infrastructure.persistence.entity.notice.Tag();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setName(domain.getName());

        // We don't map notices to avoid circular references
        // This will be handled by the NoticeMapper
        entity.setNotices(new HashSet<>());

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.Tag updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.notice.Tag entity, Tag domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setName(domain.getName());

        // We don't update notices to avoid circular references

        return entity;
    }
}
