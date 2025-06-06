package io.github.ruifoot.core.service.auth;

import io.github.ruifoot.common.exception.CustomException;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.github.ruifoot.infrastructure.cache.redis.RedisService;
import io.github.ruifoot.infrastructure.security.jwt.JwtService;
import io.github.ruifoot.infrastructure.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public String getUsername(long id) {
        return userRepository.findById(id)
                .map(Users::getUsername)
                .orElse(String.valueOf(id));
    }

    @Override
    public Users register(String username, String email, String password) {
        // Check if username or email already exists
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user
        Users user = new Users();
        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setAdminApproved(false);

        // Save user
        return userRepository.save(user);
    }

    @Override
    public JwtToken login(String email, String password) {
        return jwtService.signIn(email, password);
    }

    @Override
    public JwtToken refreshToken(String refreshToken) {
        return jwtService.refreshToken(refreshToken);
    }

    @Override
    public boolean logout(String refreshToken) {
        // 1. Refresh Token 유효성 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ResponseCode.INVALID_TOKEN, ResponseCode.INVALID_TOKEN.getMessage());
        }


        if (!redisService.hasKey(refreshToken)) {
            return false; // 이미 삭제되었거나 존재하지 않음
        }

        redisService.deleteValues(refreshToken);
        return true; // 삭제 성공
    }
}
