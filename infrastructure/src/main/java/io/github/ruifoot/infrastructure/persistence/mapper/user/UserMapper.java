package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User entity and Users domain model.
 */
@Component
public class UserMapper implements EntityMapper<UsersEntity, io.github.ruifoot.domain.model.user.Users> {

    @Override
    public Users toDomain(UsersEntity entity) {
        if (entity == null) {
            return null;
        }

        return Users.builder()
                .id(entity.getId() != null ? entity.getId() : 0)
                .username(entity.getUsername())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .role(entity.getRole())
                .adminApproved(entity.isEnabled())
                .build();
    }

    @Override
    public UsersEntity toEntity(io.github.ruifoot.domain.model.user.Users domain) {
        if (domain == null) {
            return null;
        }

        UsersEntity entity = new UsersEntity();

        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }

        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setRole(domain.getRole());
        entity.setAdminApproved(domain.isAdminApproved());

        // createdAt and updatedAt are automatically managed by Hibernate
        // with @CreationTimestamp and @UpdateTimestamp annotations
        // No need to set them manually

        return entity;
    }

    @Override
    public UsersEntity updateEntityFromDomain(UsersEntity entity, io.github.ruifoot.domain.model.user.Users domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setUsername(domain.getUsername());
        entity.setEmail(domain.getEmail());
        entity.setPasswordHash(domain.getPasswordHash());
        entity.setRole(domain.getRole());

        // Note: updatedAt will be automatically updated by JPA/Hibernate

        return entity;
    }
}
