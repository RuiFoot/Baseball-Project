package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.NoticeViewLog;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between NoticeViewLog entity and NoticeViewLog domain model.
 */
@Component
public class NoticeViewLogMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog, NoticeViewLog> {

    private final NoticeMapper noticeMapper;
    private final UserMapper userMapper;

    public NoticeViewLogMapper(NoticeMapper noticeMapper, UserMapper userMapper) {
        this.noticeMapper = noticeMapper;
        this.userMapper = userMapper;
    }

    @Override
    public NoticeViewLog toDomain(io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog entity) {
        if (entity == null) {
            return null;
        }

        NoticeViewLog domain = new NoticeViewLog();
        domain.setId(entity.getId());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getNotice() != null) {
            domain.setNotice(noticeMapper.toDomain(entity.getNotice()));
        }
        
        if (entity.getViewer() != null) {
            domain.setViewer(userMapper.toDomain(entity.getViewer()));
        }

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog toEntity(NoticeViewLog domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog entity = new io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        if (domain.getNotice() != null) {
            entity.setNotice(noticeMapper.toEntity(domain.getNotice()));
        }
        
        if (domain.getViewer() != null) {
            entity.setViewer(userMapper.toEntity(domain.getViewer()));
        }

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.notice.NoticeViewLog entity, NoticeViewLog domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        if (domain.getNotice() != null) {
            entity.setNotice(noticeMapper.toEntity(domain.getNotice()));
        }
        
        if (domain.getViewer() != null) {
            entity.setViewer(userMapper.toEntity(domain.getViewer()));
        }

        return entity;
    }
}