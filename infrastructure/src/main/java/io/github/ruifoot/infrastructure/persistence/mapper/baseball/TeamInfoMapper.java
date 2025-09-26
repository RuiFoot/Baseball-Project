package io.github.ruifoot.infrastructure.persistence.mapper.baseball;

import io.github.ruifoot.domain.model.baseball.TeamInfo;
import io.github.ruifoot.infrastructure.persistence.entity.baseball.TeamInfoEntity;
import io.github.ruifoot.infrastructure.persistence.mapper.EntityMapper;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between TeamInfo entity and TeamInfo domain model.
 */
@Component
public class TeamInfoMapper implements EntityMapper<TeamInfoEntity, TeamInfo> {

    @Override
    public TeamInfo toDomain(TeamInfoEntity entity) {
        if (entity == null) {
            return null;
        }

        TeamInfo domain = new TeamInfo();
        domain.setId(entity.getId());
        domain.setRegion(entity.getRegion());
        domain.setLeague(entity.getLeague());
        domain.setTrainingPlace(entity.getTrainingPlace());
        domain.setManager(entity.getManager());
        domain.setNotice(entity.getNotice());
        domain.setTrainingSchedule(entity.getTrainingSchedule());
        domain.setAverageAge(entity.getAverageAge());
        domain.setMemberCount(entity.getMemberCount());
        domain.setMonthlyFee(entity.getMonthlyFee());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        // Avoid circular reference by not setting teams here

        return domain;
    }

    @Override
    public TeamInfoEntity toEntity(TeamInfo domain) {
        if (domain == null) {
            return null;
        }

        TeamInfoEntity entity = new TeamInfoEntity();

        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }

        entity.setRegion(domain.getRegion());
        entity.setLeague(domain.getLeague());
        entity.setTrainingPlace(domain.getTrainingPlace());
        entity.setManager(domain.getManager());
        entity.setNotice(domain.getNotice());
        entity.setTrainingSchedule(domain.getTrainingSchedule());
        entity.setAverageAge(domain.getAverageAge());
        entity.setMemberCount(domain.getMemberCount());
        entity.setMonthlyFee(domain.getMonthlyFee());

        // Avoid circular reference by not setting teams here

        return entity;
    }

    @Override
    public TeamInfoEntity updateEntityFromDomain(
            TeamInfoEntity entity, TeamInfo domain) {
        if (entity == null || domain == null) {
            return entity;
        }

        // Don't update ID as it's the primary key
        entity.setRegion(domain.getRegion());
        entity.setLeague(domain.getLeague());
        entity.setTrainingPlace(domain.getTrainingPlace());
        entity.setManager(domain.getManager());
        entity.setNotice(domain.getNotice());
        entity.setTrainingSchedule(domain.getTrainingSchedule());
        entity.setAverageAge(domain.getAverageAge());
        entity.setMemberCount(domain.getMemberCount());
        entity.setMonthlyFee(domain.getMonthlyFee());

        // Avoid circular reference by not updating teams here

        return entity;
    }
}
