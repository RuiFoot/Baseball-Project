package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseballEntity;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBaseballJpaRepository extends JpaRepository<UserBaseballEntity, Integer> {
    Optional<UserBaseballEntity> findByUser(UsersEntity usersEntity);
}