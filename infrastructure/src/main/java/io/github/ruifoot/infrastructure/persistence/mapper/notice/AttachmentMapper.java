package io.github.ruifoot.infrastructure.persistence.mapper.notice;

import io.github.ruifoot.domain.model.notice.Attachment;
import io.github.ruifoot.infrastructure.persistence.entity.notice.AttachmentEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Attachment entity and Attachment domain model.
 */
@Component
public class AttachmentMapper implements EntityMapper<AttachmentEntity, Attachment> {

    private final NoticeMapper noticeMapper;

    public AttachmentMapper(NoticeMapper noticeMapper) {
        this.noticeMapper = noticeMapper;
    }

    @Override
    public Attachment toDomain(AttachmentEntity entity) {
        if (entity == null) {
            return null;
        }

        Attachment domain = new Attachment();
        domain.setId(entity.getId());
        domain.setFileName(entity.getFileName());
        domain.setFileUrl(entity.getFileUrl());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        
        if (entity.getNoticeEntity() != null) {
            domain.setNotice(noticeMapper.toDomain(entity.getNoticeEntity()));
        }

        return domain;
    }

    @Override
    public AttachmentEntity toEntity(Attachment domain) {
        if (domain == null) {
            return null;
        }

        AttachmentEntity entity = new AttachmentEntity();
        
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        
        entity.setFileName(domain.getFileName());
        entity.setFileUrl(domain.getFileUrl());
        
        if (domain.getNotice() != null) {
            entity.setNoticeEntity(noticeMapper.toEntity(domain.getNotice()));
        }

        return entity;
    }

    @Override
    public AttachmentEntity updateEntityFromDomain(
            AttachmentEntity entity, Attachment domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setFileName(domain.getFileName());
        entity.setFileUrl(domain.getFileUrl());
        
        if (domain.getNotice() != null) {
            entity.setNoticeEntity(noticeMapper.toEntity(domain.getNotice()));
        }

        return entity;
    }
}