package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.Comment;
import io.github.ruifoot.infrastructure.persistence.entity.notice.CommentEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Comment entity and Comment domain model.
 */
@Component
public class CommentMapper implements EntityMapper<CommentEntity, Comment> {

    private final NoticeMapper noticeMapper;
    private final UserMapper userMapper;

    public CommentMapper(NoticeMapper noticeMapper, UserMapper userMapper) {
        this.noticeMapper = noticeMapper;
        this.userMapper = userMapper;
    }

    @Override
    public Comment toDomain(CommentEntity entity) {
        if (entity == null) {
            return null;
        }

        Comment domain = new Comment();
        domain.setId(entity.getId());
        domain.setContent(entity.getContent());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getAuthor() != null) {
            domain.setAuthor(userMapper.toDomain(entity.getAuthor()));
        }
        
        if (entity.getNoticeEntity() != null) {
            domain.setNotice(noticeMapper.toDomain(entity.getNoticeEntity()));
        }

        return domain;
    }

    @Override
    public CommentEntity toEntity(Comment domain) {
        if (domain == null) {
            return null;
        }

        CommentEntity entity = new CommentEntity();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        entity.setContent(domain.getContent());
        
        if (domain.getAuthor() != null) {
            entity.setAuthor(userMapper.toEntity(domain.getAuthor()));
        }
        
        if (domain.getNotice() != null) {
            entity.setNoticeEntity(noticeMapper.toEntity(domain.getNotice()));
        }

        return entity;
    }

    @Override
    public CommentEntity updateEntityFromDomain(
            CommentEntity entity, Comment domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setContent(domain.getContent());
        
        if (domain.getAuthor() != null) {
            entity.setAuthor(userMapper.toEntity(domain.getAuthor()));
        }
        
        if (domain.getNotice() != null) {
            entity.setNoticeEntity(noticeMapper.toEntity(domain.getNotice()));
        }

        return entity;
    }
}