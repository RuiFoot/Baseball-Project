package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.baseball.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamJpaRepository extends JpaRepository<Teams, Long> {
}
