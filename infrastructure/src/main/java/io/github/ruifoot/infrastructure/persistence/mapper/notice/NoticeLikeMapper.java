package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.NoticeLike;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between NoticeLike entity and NoticeLike domain model.
 */
@Component
public class NoticeLikeMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike, NoticeLike> {

    private final NoticeMapper noticeMapper;
    private final UserMapper userMapper;

    public NoticeLikeMapper(NoticeMapper noticeMapper, UserMapper userMapper) {
        this.noticeMapper = noticeMapper;
        this.userMapper = userMapper;
    }

    @Override
    public NoticeLike toDomain(io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike entity) {
        if (entity == null) {
            return null;
        }

        NoticeLike domain = new NoticeLike();
        domain.setId(entity.getId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getNotice() != null) {
            domain.setNotice(noticeMapper.toDomain(entity.getNotice()));
        }
        
        if (entity.getUser() != null) {
            domain.setUser(userMapper.toDomain(entity.getUser()));
        }

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike toEntity(NoticeLike domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike entity = new io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        if (domain.getNotice() != null) {
            entity.setNotice(noticeMapper.toEntity(domain.getNotice()));
        }
        
        if (domain.getUser() != null) {
            entity.setUser(userMapper.toEntity(domain.getUser()));
        }

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLike entity, NoticeLike domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        if (domain.getNotice() != null) {
            entity.setNotice(noticeMapper.toEntity(domain.getNotice()));
        }
        
        if (domain.getUser() != null) {
            entity.setUser(userMapper.toEntity(domain.getUser()));
        }

        return entity;
    }
}