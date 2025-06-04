package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User entity and Users domain model.
 */
@Component
public class UserMapper implements EntityMapper<Users, io.github.ruifoot.domain.model.user.Users> {

    @Override
    public io.github.ruifoot.domain.model.user.Users toDomain(Users entity) {
        if (entity == null) {
            return null;
        }

        io.github.ruifoot.domain.model.user.Users domain = new io.github.ruifoot.domain.model.user.Users();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        domain.setUsername(entity.getUsername());
        domain.setEmail(entity.getEmail());
        domain.setPasswordHash(entity.getPasswordHash());
        domain.setRole(entity.getRole());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());
        domain.setAdminApproved(entity.isEnabled());

        return domain;
    }

    @Override
    public Users toEntity(io.github.ruifoot.domain.model.user.Users domain) {
        if (domain == null) {
            return null;
        }

        Users entity = new Users();

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
    public Users updateEntityFromDomain(Users entity, io.github.ruifoot.domain.model.user.Users domain) {
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
