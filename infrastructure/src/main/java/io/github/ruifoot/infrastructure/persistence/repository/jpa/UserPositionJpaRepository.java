package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball;
import io.github.ruifoot.infrastructure.persistence.entity.user.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPositionJpaRepository extends JpaRepository<UserPosition, Integer> {
    List<UserPosition> findByUserBaseball(UserBaseball userBaseball);
}