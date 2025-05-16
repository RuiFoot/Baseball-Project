package io.github.ruifoot.infrastructure.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final JwtTokenProvider jwtTokenProvider;

    public Authentication getAuthentication(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            return null;
        }

        String userId = jwtTokenProvider.getUserIdFromToken(token);

        // 실제 사용 시에는 UserDetailsService 등에서 User 객체를 불러오지만
        // 여기서는 간단히 principal 에 userId 만 세팅
        return new UsernamePasswordAuthenticationToken(userId, null, null);
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
    public boolean validateToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }
}