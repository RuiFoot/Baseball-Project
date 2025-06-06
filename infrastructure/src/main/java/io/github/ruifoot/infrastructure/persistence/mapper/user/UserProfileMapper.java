package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.UserProfiles;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserProfile;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

import java.sql.Date;


/**
 * Mapper for converting between UserProfile entity and UserProfiles domain model.
 */
@Component
public class UserProfileMapper implements EntityMapper<UserProfile, UserProfiles> {

    @Override
    public UserProfiles toDomain(UserProfile entity) {
        if (entity == null) {
            return null;
        }

        UserProfiles domain = new UserProfiles();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        
        // Map User entity to userId
        if (entity.getUsers() != null && entity.getUsers().getId() != null) {
            domain.setUserId(entity.getUsers().getId());
        }
        
        domain.setFullName(entity.getFullName());
        
        // Convert LocalDate to java.sql.Date
        if (entity.getBirthDate() != null) {
            domain.setBirthDate(Date.valueOf(entity.getBirthDate()));
        }
        
        domain.setPhone(entity.getPhone());
        domain.setResidence(entity.getResidence());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    @Override
    public UserProfile toEntity(UserProfiles domain) {
        if (domain == null) {
            return null;
        }

        UserProfile entity = new UserProfile();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        // For User reference, we only set the ID
        // The actual User object should be loaded by the repository
        if (domain.getUserId() > 0) {
            Users users = new Users();
            users.setId((int) domain.getUserId());
            entity.setUsers(users);
        }
        
        entity.setFullName(domain.getFullName());
        
        // Convert java.sql.Date to LocalDate
        if (domain.getBirthDate() != null) {
            entity.setBirthDate(domain.getBirthDate().toLocalDate());
        }
        
        entity.setPhone(domain.getPhone());
        entity.setResidence(domain.getResidence());
        
        // Note: createdAt and updatedAt are managed by JPA/Hibernate

        return entity;
    }

    @Override
    public UserProfile updateEntityFromDomain(UserProfile entity, UserProfiles domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        // For User reference, we only update if the userId has changed
        if (domain.getUserId() > 0 && 
            (entity.getUsers() == null || entity.getUsers().getId() != domain.getUserId())) {
            Users users = new Users();
            users.setId((int) domain.getUserId());
            entity.setUsers(users);
        }
        
        entity.setFullName(domain.getFullName());
        
        // Convert java.sql.Date to LocalDate
        if (domain.getBirthDate() != null) {
            entity.setBirthDate(domain.getBirthDate().toLocalDate());
        } else {
            entity.setBirthDate(null);
        }
        
        entity.setPhone(domain.getPhone());
        entity.setResidence(domain.getResidence());
        
        // Note: updatedAt will be automatically updated by JPA/Hibernate

        return entity;
    }
}