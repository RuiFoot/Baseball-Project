package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.Tag;
import io.github.ruifoot.infrastructure.persistence.entity.notice.TagsEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * Mapper for converting between Tag entity and Tag domain model.
 */
@Component
public class TagMapper implements EntityMapper<TagsEntity, Tag> {

    @Override
    public Tag toDomain(TagsEntity entity) {
        if (entity == null) {
            return null;
        }

        // We don't map notices to avoid circular references
        // This will be handled by the NoticeMapper

        return Tag.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public TagsEntity toEntity(Tag domain) {
        if (domain == null) {
            return null;
        }

        TagsEntity entity = new TagsEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setName(domain.getName());

        // We don't map notices to avoid circular references
        // This will be handled by the NoticeMapper
        entity.setNoticeEntities(new ArrayList<>());

        return entity;
    }

    @Override
    public TagsEntity updateEntityFromDomain(
            TagsEntity entity, Tag domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setName(domain.getName());

        // We don't update notices to avoid circular references

        return entity;
    }
}
