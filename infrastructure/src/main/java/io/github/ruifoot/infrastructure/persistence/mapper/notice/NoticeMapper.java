package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.domain.model.notice.Notice;
import io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Notice entity and Notice domain model.
 */
@Component
public class NoticeMapper implements EntityMapper<NoticeEntity, Notice> {

    private final UserMapper userMapper;
    private final TagMapper tagMapper;

    public NoticeMapper(UserMapper userMapper, TagMapper tagMapper) {
        this.userMapper = userMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public Notice toDomain(NoticeEntity entity) {
        if (entity == null) {
            return null;
        }

        Notice domain = new Notice();
        domain.setId(entity.getId());
        domain.setTitle(entity.getTitle());
        domain.setContent(entity.getContent());
        domain.setViewCount(entity.getViewCount());
        domain.setPinned(entity.isPinned());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getAuthor() != null) {
            domain.setAuthor(userMapper.toDomain(entity.getAuthor()));
        }
        
        if (entity.getTagsEntity() != null) {
            domain.setTag(tagMapper.toDomain(entity.getTagsEntity()));
        }

        return domain;
    }

    @Override
    public NoticeEntity toEntity(Notice domain) {
        if (domain == null) {
            return null;
        }

        NoticeEntity entity = new NoticeEntity();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        entity.setTitle(domain.getTitle());
        entity.setContent(domain.getContent());
        entity.setViewCount(domain.getViewCount());
        entity.setPinned(domain.isPinned());
        
        if (domain.getAuthor() != null) {
            entity.setAuthor(userMapper.toEntity(domain.getAuthor()));
        }
        
        if (domain.getTag() != null) {
            entity.setTagsEntity(tagMapper.toEntity(domain.getTag()));
        }

        return entity;
    }

    @Override
    public NoticeEntity updateEntityFromDomain(
            NoticeEntity entity, Notice domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setTitle(domain.getTitle());
        entity.setContent(domain.getContent());
        entity.setViewCount(domain.getViewCount());
        entity.setPinned(domain.isPinned());
        
        if (domain.getAuthor() != null) {
            entity.setAuthor(userMapper.toEntity(domain.getAuthor()));
        }
        
        if (domain.getTag() != null) {
            entity.setTagsEntity(tagMapper.toEntity(domain.getTag()));
        }

        return entity;
    }

    public NoticeResponseDto toResponseDto(NoticeEntity entity) {
        if (entity == null) {
            return null;
        }

        return new NoticeResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViewCount(),
                entity.isPinned(),
                entity.getAuthor() != null ? entity.getAuthor().getUsername() : null,
                entity.getTagsEntity() != null ? entity.getTagsEntity().getName() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
