package io.github.ruifoot.api.controller;

import io.github.ruifoot.api.dto.auth.request.LoginRequest;
import io.github.ruifoot.api.dto.auth.request.RefreshTokenRequest;
import io.github.ruifoot.api.dto.auth.request.SignupRequest;
import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ApiResponseUtil;
import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.github.ruifoot.domain.service.users.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 인증, 인가를 위한 컨트롤러
 * 로그인, 회원가입, 토큰 발급/갱신, 인증 관련 기능
 */
@RestController(value = "auth")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<CommonResponseDto<?>> me() {
        String username = userService.getUsername(1L);
        return ApiResponseUtil.success(ResponseCode.SUCCESS, username);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<?>> login(@RequestBody @Valid LoginRequest request) {
        try {
            JwtToken jwtToken = authService.login(request.email(), request.password());
            return ApiResponseUtil.success(ResponseCode.LOGIN_SUCCESS, jwtToken);
        } catch (RuntimeException e) {
            return ApiResponseUtil.fail(ResponseCode.INVALID_PASSWORD, e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<?>> signup(@RequestBody @Valid SignupRequest request) {
        try {
            Users user = authService.register(request.username(), request.email(), request.password());
            return ApiResponseUtil.success(ResponseCode.USER_CREATE_SUCCESS, user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("exists")) {
                return ApiResponseUtil.fail(ResponseCode.DUPLICATED_USER, e.getMessage());
            }
            return ApiResponseUtil.fail(ResponseCode.INVALID_PARAMETER, e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<CommonResponseDto<?>> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        try {
            JwtToken jwtToken = authService.refreshToken(request.refreshToken());
            return ApiResponseUtil.success(ResponseCode.SUCCESS, jwtToken);
        } catch (RuntimeException e) {
            return ApiResponseUtil.fail(ResponseCode.INVALID_TOKEN, e.getMessage());
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<CommonResponseDto<?>> logout(@RequestParam @NotBlank String token) {
        try {
            authService.logout(token);
            return ApiResponseUtil.success(ResponseCode.LOGOUT_SUCCESS);
        } catch (RuntimeException e) {
            return ApiResponseUtil.fail(ResponseCode.INVALID_TOKEN, e.getMessage());
        }
    }

    /*
    TODO[AuthController]: 비밀번호 재설정 기능 필요
     */

    /*
    TODO[AuthController]: 소셜 로그인 사용시 기능 추가 필요
     */
}
