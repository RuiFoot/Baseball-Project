package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseballEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserPositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPositionJpaRepository extends JpaRepository<UserPositionEntity, Integer> {
    List<UserPositionEntity> findByUserBaseball(UserBaseballEntity userBaseballEntity);
}