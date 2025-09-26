package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.user.UserProfileEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, Integer> {
    Optional<UserProfileEntity> findByUser(UsersEntity usersEntity);
}