package io.github.ruifoot.infrastructure.persistence.mapper.user;

import io.github.ruifoot.domain.model.user.UserBaseball;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamsEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseballEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between UserBaseball entity and UserBaseball domain model.
 */
@Component
public class UserBaseballMapper implements EntityMapper<UserBaseballEntity, UserBaseball> {

    @Override
    public UserBaseball toDomain(UserBaseballEntity entity) {
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
        if (entity.getTeamsEntity() != null && entity.getTeamsEntity().getId() != null) {
            domain.setTeamId(entity.getTeamsEntity().getId());
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
    public UserBaseballEntity toEntity(UserBaseball domain) {
        if (domain == null) {
            return null;
        }

        UserBaseballEntity entity = new UserBaseballEntity();
        
        // Don't set ID for new entities (ID is auto-generated)
        if (domain.getId() > 0) {
            entity.setId((int) domain.getId());
        }
        
        // For User reference, we only set the ID
        // The actual User object should be loaded by the repository
        if (domain.getUserId() > 0) {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setId((int) domain.getUserId());
            entity.setUser(usersEntity);
        }
        
        // For Team reference, we only set the ID
        // The actual Team object should be loaded by the repository
        if (domain.getTeamId() > 0) {
            TeamsEntity teamsEntity = new TeamsEntity();
            teamsEntity.setId((int) domain.getTeamId());
            entity.setTeamsEntity(teamsEntity);
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
    public UserBaseballEntity updateEntityFromDomain(
            UserBaseballEntity entity, UserBaseball domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        
        // For User reference, we only update if the userId has changed
        if (domain.getUserId() > 0 && 
            (entity.getUser() == null || entity.getUser().getId() != domain.getUserId())) {
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setId((int) domain.getUserId());
            entity.setUser(usersEntity);
        }
        
        // For Team reference, we only update if the teamId has changed
        if (domain.getTeamId() > 0 && 
            (entity.getTeamsEntity() == null || entity.getTeamsEntity().getId() != domain.getTeamId())) {
            TeamsEntity teamsEntity = new TeamsEntity();
            teamsEntity.setId((int) domain.getTeamId());
            entity.setTeamsEntity(teamsEntity);
        } else if (domain.getTeamId() == 0) {
            entity.setTeamsEntity(null);
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