package io.github.ruifoot.infrastructure.persistence.repository.impl;

import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.infrastructure.persistence.entity.user.User;
import io.github.ruifoot.infrastructure.persistence.mapper.user.UserMapper;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<Users> findById(long id) {
        return userJpaRepository.findById((int) id)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userJpaRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public Users save(Users user) {
        User entity = userMapper.toEntity(user);
        User savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }
}
