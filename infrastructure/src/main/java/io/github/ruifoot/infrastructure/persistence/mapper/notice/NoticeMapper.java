package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.Notice;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Mapper for converting between Notice entity and Notice domain model.
 */
@Component
public class NoticeMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.notice.Notice, Notice> {

    private final UserMapper userMapper;
    private final TagMapper tagMapper;

    public NoticeMapper(UserMapper userMapper, TagMapper tagMapper) {
        this.userMapper = userMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public Notice toDomain(io.github.ruifoot.infrastructure.persistence.entity.notice.Notice entity) {
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
        
        if (entity.getTags() != null) {
            domain.setTags(entity.getTags().stream()
                    .map(tagMapper::toDomain)
                    .collect(Collectors.toSet()));
        }

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.Notice toEntity(Notice domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.notice.Notice entity = new io.github.ruifoot.infrastructure.persistence.entity.notice.Notice();
        
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
        
        if (domain.getTags() != null) {
            entity.setTags(domain.getTags().stream()
                    .map(tagMapper::toEntity)
                    .collect(Collectors.toSet()));
        } else {
            entity.setTags(new HashSet<>());
        }

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.Notice updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.notice.Notice entity, Notice domain) {
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
        
        if (domain.getTags() != null) {
            entity.setTags(domain.getTags().stream()
                    .map(tagMapper::toEntity)
                    .collect(Collectors.toSet()));
        }

        return entity;
    }
}