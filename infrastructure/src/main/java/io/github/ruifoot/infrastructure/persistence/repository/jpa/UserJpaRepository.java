package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UsersEntity, Integer> {
    Optional<UsersEntity> findByUsername(String username);
    Optional<UsersEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    UsersEntity getReferenceByUsername(String username);
}