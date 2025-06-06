package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.UserBaseball;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserBaseball entity and UserBaseball domain model.
 */
@Component
public class UserBaseballMapper implements EntityMapper<io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball, UserBaseball> {

    @Override
    public UserBaseball toDomain(io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball entity) {
        if (entity == null) {
            return null;
        }

        UserBaseball domain = new UserBaseball();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        
        // Map User entity to userId
        if (entity.getUsers() != null && entity.getUsers().getId() != null) {
            domain.setUserId(entity.getUsers().getId());
        }
        
        // Map Team entity to teamId
        if (entity.getTeams() != null && entity.getTeams().getId() != null) {
            domain.setTeamId(entity.getTeams().getId());
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
    public io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball toEntity(UserBaseball domain) {
        if (domain == null) {
            return null;
        }

        io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball entity = new io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball();
        
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
        
        // For Team reference, we only set the ID
        // The actual Team object should be loaded by the repository
        if (domain.getTeamId() > 0) {
            Teams teams = new Teams();
            teams.setId((int) domain.getTeamId());
            entity.setTeams(teams);
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
    public io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball updateEntityFromDomain(
            io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball entity, UserBaseball domain) {
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
        
        // For Team reference, we only update if the teamId has changed
        if (domain.getTeamId() > 0 && 
            (entity.getTeams() == null || entity.getTeams().getId() != domain.getTeamId())) {
            Teams teams = new Teams();
            teams.setId((int) domain.getTeamId());
            entity.setTeams(teams);
        } else if (domain.getTeamId() == 0) {
            entity.setTeams(null);
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