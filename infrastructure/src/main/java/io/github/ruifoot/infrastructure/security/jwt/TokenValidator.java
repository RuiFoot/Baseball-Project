package io.github.ruifoot.infrastructure.security.jwt;

import io.github.ruifoot.infrastructure.persistence.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuthentication(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return null;
        }

        Claims claims = jwtTokenProvider.parseClaims(token);

        // Log extracted claims for debugging
        log.info("Claims: {}", claims);

        if (claims.get("authorities") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("authorities").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // Request Header에서 토큰 정보 추출
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer")) {
            return bearer.substring(7);
        }
        return null;
    }
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}
