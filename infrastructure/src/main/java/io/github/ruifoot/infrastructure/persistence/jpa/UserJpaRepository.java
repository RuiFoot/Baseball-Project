package io.github.ruifoot.infrastructure.persistence.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
