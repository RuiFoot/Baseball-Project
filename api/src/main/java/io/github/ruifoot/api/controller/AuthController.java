package io.github.ruifoot.api.controller;

import io.github.ruifoot.api.dto.auth.request.AdminApprovalRequest;
import io.github.ruifoot.api.dto.auth.request.AdminRegisterRequest;
import io.github.ruifoot.api.dto.auth.request.LoginRequest;
import io.github.ruifoot.api.dto.auth.request.RefreshTokenRequest;
import io.github.ruifoot.api.dto.auth.request.RegisterRequest;
import io.github.ruifoot.api.mapper.RegisterMapper;
import io.github.ruifoot.common.dto.ResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ResponseUtil;
import io.github.ruifoot.domain.dto.auth.request.RegisterDto;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.github.ruifoot.domain.service.user.UserService;
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
    public ResponseEntity<ResponseDto<?>> me() {
        String username = userService.getUsername(1L);
        return ResponseUtil.success(ResponseCode.SUCCESS, username);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> login(@RequestBody @Valid LoginRequest request) {
        try {
            JwtToken jwtToken = authService.login(request.email(), request.password());
            return ResponseUtil.success(ResponseCode.LOGIN_SUCCESS, jwtToken);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_PASSWORD, e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<?>> registerUser(@RequestBody @Valid RegisterRequest request) {
        try {
            // Convert API SignupRequest to Core SignupRequest
            RegisterDto coreDto = RegisterMapper.toCore(request);

            // Call the service with the core SignupRequest
            Users user = authService.register(coreDto);

            return ResponseUtil.success(ResponseCode.USER_CREATE_SUCCESS, user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("exists")) {
                return ResponseUtil.fail(ResponseCode.DUPLICATED_USER, e.getMessage());
            }
            return ResponseUtil.fail(ResponseCode.INVALID_PARAMETER, e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<?>> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        try {
            JwtToken jwtToken = authService.refreshToken(request.refreshToken());
            return ResponseUtil.success(ResponseCode.SUCCESS, jwtToken);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_TOKEN, e.getMessage());
        }
    }
    //TODO 리프레시토큰으로만 대응되게, 토큰이 이상하면 로그아웃 실패 뜨게하기
    @DeleteMapping("/logout")
    public ResponseEntity<ResponseDto<?>> logout(@RequestParam @NotBlank String token) {
        try {
            authService.logout(token);
            return ResponseUtil.success(ResponseCode.LOGOUT_SUCCESS);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_TOKEN, e.getMessage());
        }
    }

    /*
    TODO[AuthController]: 비밀번호 재설정 기능 필요
     */

    /*
    TODO[AuthController]: 소셜 로그인 사용시 기능 추가 필요
     */
    /*
    DONE[AuthController]: 관리자 계정 생성 기능 추가 필요
    DONE[AuthController]: 관리자 계정으로 admin_approved 체크해주는 기능 필요
     */

    /**
     * Register a new admin user
     * @param request Admin registration request
     * @return Response with created admin user
     */
    @PostMapping("/admin/signup")
    public ResponseEntity<ResponseDto<?>> registerAdmin(@RequestBody @Valid AdminRegisterRequest request) {
        try {
            // Convert API AdminRegisterRequest to Core AdminRegisterDto
            var coreDto = RegisterMapper.toCoreAdmin(request);

            // Call the service with the core AdminRegisterDto
            Users user = authService.registerAdmin(coreDto);

            return ResponseUtil.success(ResponseCode.USER_CREATE_SUCCESS, user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("exists")) {
                return ResponseUtil.fail(ResponseCode.DUPLICATED_USER, e.getMessage());
            }
            return ResponseUtil.fail(ResponseCode.INVALID_PARAMETER, e.getMessage());
        }
    }

    /**
     * Update admin approval status for a user
     * @param request Admin approval request
     * @return Response with updated user
     */
    @PutMapping("/admin/approval")
    public ResponseEntity<ResponseDto<?>> updateAdminApproval(@RequestBody @Valid AdminApprovalRequest request) {
        try {
            // Convert API AdminApprovalRequest to Core AdminApprovalDto
            var coreDto = RegisterMapper.toCoreApproval(request);

            // Call the service with the core AdminApprovalDto
            Users user = authService.updateAdminApproval(coreDto);

            return ResponseUtil.success(ResponseCode.SUCCESS, user);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_PARAMETER, e.getMessage());
        }
    }
}
