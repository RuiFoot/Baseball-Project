package io.github.ruifoot.infrastructure.security.jwt;

import io.github.ruifoot.common.exception.CustomException;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.infrastructure.persistence.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenValidityInMs;
    private final long refreshTokenValidityInMs;
    private static final String BEARER_TYPE = "Bearer";

    public JwtTokenProvider(@Value("${jwt.secret}") String secret,
                            @Value("${jwt.access-token-expiration}") long accessTokenValidityInMs,
                            @Value("${jwt.refresh-token-expiration}") long refreshTokenValidityInMs) {
        if (secret == null || secret.isEmpty()) {
            throw new IllegalArgumentException("JWT secret key must not be null or empty");
        }else {
            log.debug("JWT secret key is {}", secret);
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenValidityInMs = accessTokenValidityInMs * 1000; // Convert to milliseconds
        this.refreshTokenValidityInMs = refreshTokenValidityInMs * 1000; // Convert to milliseconds
    }

    public JwtToken generateToken(Authentication authentication) {

        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // null인 경우 에러 방지를 위해 임의의 값 설정했습니다
        if (authorities.isEmpty()) {
            authorities = "USER";
        }

        User user = (User) authentication.getPrincipal();


        // Generate access token
        String accessToken = createAccessToken(user, authentication, authorities);

        // Generate refresh token
        String refreshToken = createRefreshToken();

        return JwtToken.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public String createAccessToken(User user, Authentication authentication, String authorities) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenValidityInMs);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("authorities",  authorities)
                .claim("userId", user.getId())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenValidityInMs);

        return Jwts.builder()
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // accessToken
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new CustomException(ResponseCode.INVALID_TOKEN,e.getMessage());
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new CustomException(ResponseCode.EXPIRED_JWT,e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new CustomException(ResponseCode.UNSUPPORTED_JWT,e.getMessage());
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new CustomException(ResponseCode.EMPTY_JWT_CLAIMS,e.getMessage());
        }
    }



}
