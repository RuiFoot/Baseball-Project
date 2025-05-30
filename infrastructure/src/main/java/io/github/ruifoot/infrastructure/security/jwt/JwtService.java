package io.github.ruifoot.infrastructure.security.jwt;

import io.github.ruifoot.common.exception.CustomException;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.domain.model.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.repository.UserRepository;
import io.github.ruifoot.infrastructure.cache.redis.RedisService;
import io.github.ruifoot.infrastructure.persistence.entity.User;
import io.github.ruifoot.infrastructure.persistence.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public JwtToken signIn(String email, String password){

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(auth);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        String userId = authentication.getName();


        redisService.setValues(
                jwtToken.getRefreshToken(), // key: refreshToken
                String.valueOf(userId),     // value: userId (String으로 변환)
                jwtTokenProvider.getRefreshTokenValidityInMs(),
                TimeUnit.MILLISECONDS
        );

        return jwtToken;
    }
    public JwtToken refreshToken(String refreshToken){
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ResponseCode.INVALID_TOKEN, ResponseCode.INVALID_TOKEN.getMessage());
        }

        String userId = redisService.getValues(refreshToken);

        if (userId == null) {
            throw new CustomException(ResponseCode.EXPIRED_JWT, ResponseCode.EXPIRED_JWT.getMessage());
        }

        // Find user by username
        Users user = userRepository.findByUsername(userId)
                .orElseThrow(() ->new CustomException(ResponseCode.USER_NOT_FOUND, ResponseCode.USER_NOT_FOUND.getMessage()));

        // 5. 사용자 정보를 Authentication 객체로 변환
        User userEntity = userMapper.toEntity(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity, null, userEntity.getAuthorities());

        redisService.deleteValues(refreshToken);
        // 6. 새로운 JWT 토큰 발급
        JwtToken newToken = jwtTokenProvider.generateToken(authentication);

        redisService.setValues(
                newToken.getRefreshToken(), // key: refreshToken
                String.valueOf(authentication.getName()),     // value: userId (String으로 변환)
                jwtTokenProvider.getRefreshTokenValidityInMs(),
                TimeUnit.MILLISECONDS
        );
        return newToken;
    }

}
