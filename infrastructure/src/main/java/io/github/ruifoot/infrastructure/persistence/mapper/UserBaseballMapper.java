package io.github.ruifoot.infrastructure.persistence.mapper;

import io.github.ruifoot.domain.model.UserBaseball;
import io.github.ruifoot.infrastructure.persistence.entity.Team;
import io.github.ruifoot.infrastructure.persistence.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserBaseball entity and UserBaseball domain model.
 */
@Component
public class UserBaseballMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.UserBaseball, UserBaseball> {

    @Override
    public UserBaseball toDomain(io.github.ruifoot.infrastructure.persistence.entity.UserBaseball entity) {
        if (entity == null) {
            return null;
        }

        UserBaseball domain = new UserBaseball();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        
        // Map User entity to userId
        if (entity.getUser() != null && entity.getUser().getId() != null) {
            domain.setUserId(entity.getUser().getId());
        }
        
        // Map Team entity to teamId
        if (entity.getTeam() != null && entity.getTeam().getId() != null) {
            domain.setTeamId(entity.getTeam().getId());
        }
        
        // Convert Integer to long for jerseyNo
        if (entity.getJerseyNo() != null) {
            domain.setJerseyNo(entity.getJerseyNo());
        }
        
        domain.setThrowingHand(entity.getThrowingHand());
        domain.setBattingHand(entity.getBattingHand());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.UserBaseball toEntity(UserBaseball domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.UserBaseball entity = new io.github.ruifoot.infrastructure.persistence.entity.UserBaseball();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        // For User reference, we only set the ID
        // The actual User object should be loaded by the repository
        if (domain.getUserId() > 0) {
            User user = new User();
            user.setId((int) domain.getUserId());
            entity.setUser(user);
        }
        
        // For Team reference, we only set the ID
        // The actual Team object should be loaded by the repository
        if (domain.getTeamId() > 0) {
            Team team = new Team();
            team.setId((int) domain.getTeamId());
            entity.setTeam(team);
        }
        
        // Convert long to Integer for jerseyNo
        if (domain.getJerseyNo() > 0) {
            entity.setJerseyNo((int) domain.getJerseyNo());
        }
        
        entity.setThrowingHand(domain.getThrowingHand());
        entity.setBattingHand(domain.getBattingHand());
        
        // Note: createdAt and updatedAt are managed by JPA/Hibernate

        return entity;
    }

    @Override
    public io.github.ruifoot.infrastructure.persistence.entity.UserBaseball updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.UserBaseball entity, UserBaseball domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        // For User reference, we only update if the userId has changed
        if (domain.getUserId() > 0 && 
            (entity.getUser() == null || entity.getUser().getId() != domain.getUserId())) {
            User user = new User();
            user.setId((int) domain.getUserId());
            entity.setUser(user);
        }
        
        // For Team reference, we only update if the teamId has changed
        if (domain.getTeamId() > 0 && 
            (entity.getTeam() == null || entity.getTeam().getId() != domain.getTeamId())) {
            Team team = new Team();
            team.setId((int) domain.getTeamId());
            entity.setTeam(team);
        } else if (domain.getTeamId() == 0) {
            entity.setTeam(null);
        }
        
        // Convert long to Integer for jerseyNo
        if (domain.getJerseyNo() > 0) {
            entity.setJerseyNo((int) domain.getJerseyNo());
        } else {
            entity.setJerseyNo(null);
        }
        
        entity.setThrowingHand(domain.getThrowingHand());
        entity.setBattingHand(domain.getBattingHand());
        
        // Note: updatedAt will be automatically updated by JPA/Hibernate

        return entity;
    }
}