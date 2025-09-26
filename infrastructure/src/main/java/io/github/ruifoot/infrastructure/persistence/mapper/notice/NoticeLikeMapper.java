package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.NoticeLike;
import io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeLikeEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between NoticeLike entity and NoticeLike domain model.
 */
@Component
public class NoticeLikeMapper implements EntityMapper<NoticeLikeEntity, NoticeLike> {

    private final NoticeMapper noticeMapper;
    private final UserMapper userMapper;

    public NoticeLikeMapper(NoticeMapper noticeMapper, UserMapper userMapper) {
        this.noticeMapper = noticeMapper;
        this.userMapper = userMapper;
    }

    @Override
    public NoticeLike toDomain(NoticeLikeEntity entity) {
        if (entity == null) {
            return null;
        }

        NoticeLike domain = new NoticeLike();
        domain.setId(entity.getId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getNoticeEntity() != null) {
            domain.setNotice(noticeMapper.toDomain(entity.getNoticeEntity()));
        }
        
        if (entity.getUsersEntity() != null) {
            domain.setUser(userMapper.toDomain(entity.getUsersEntity()));
        }

        return domain;
    }

    @Override
    public NoticeLikeEntity toEntity(NoticeLike domain) {
        if (domain == null) {
            return null;
        }

        NoticeLikeEntity entity = new NoticeLikeEntity();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        if (domain.getNotice() != null) {
            entity.setNoticeEntity(noticeMapper.toEntity(domain.getNotice()));
        }
        
        if (domain.getUser() != null) {
            entity.setUsersEntity(userMapper.toEntity(domain.getUser()));
        }

        return entity;
    }

    @Override
    public NoticeLikeEntity updateEntityFromDomain(
            NoticeLikeEntity entity, NoticeLike domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        if (domain.getNotice() != null) {
            entity.setNoticeEntity(noticeMapper.toEntity(domain.getNotice()));
        }
        
        if (domain.getUser() != null) {
            entity.setUsersEntity(userMapper.toEntity(domain.getUser()));
        }

        return entity;
    }
}