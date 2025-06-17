package io.github.ruifoot.infrastructure.persistence.repository.jpa;

import io.github.ruifoot.infrastructure.persistence.entity.user.UserBaseball;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBaseballJpaRepository extends JpaRepository<UserBaseball, Integer> {
    Optional<UserBaseball> findByUsers(Users users);
}