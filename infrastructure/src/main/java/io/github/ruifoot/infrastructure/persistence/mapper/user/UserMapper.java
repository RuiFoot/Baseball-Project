package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.infrastructure.persistence.entity.user.User;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User entity and Users domain model.
 */
@Component
public class UserMapper implements EntityMapper<User, Users> {

    @Override
    public Users toDomain(User entity) {
        if (entity == null) {
            return null;
        }

        Users domain = new Users();
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
    public User toEntity(Users domain) {
        if (domain == null) {
            return null;
        }

        User entity = new User();

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
    public User updateEntityFromDomain(User entity, Users domain) {
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
