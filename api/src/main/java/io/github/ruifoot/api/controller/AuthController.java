package io.github.ruifoot.api.controller;

import io.github.ruifoot.common.dto.auth.request.*;
import io.github.ruifoot.common.dto.common.ResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ResponseUtil;
import io.github.ruifoot.domain.model.auth.JwtToken;
import io.github.ruifoot.domain.model.user.Users;
import io.github.ruifoot.domain.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증, 인가를 위한 컨트롤러
 * 로그인, 회원가입, 토큰 발급/갱신, 인증 관련 기능
 */
@Tag(name = "Auth", description = "인증/인가 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth") // API 버전 명시
public class AuthController {

    private final AuthService authService;

    /**
     * 사용자의 이메일과 비밀번호로 로그인합니다.
     *
     * @param request 로그인 요청 정보 (이메일, 비밀번호)
     * @return JWT 토큰 (액세스 토큰, 리프레시 토큰)
     */
    @Operation(summary = "로그인", description = "이메일과 비밀번호를 사용하여 로그인하고 JWT를 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<?>> login(@RequestBody @Valid LoginDto request) {
        try {
            JwtToken jwtToken = authService.login(request.email(), request.password());
            return ResponseUtil.success(ResponseCode.LOGIN_SUCCESS, jwtToken);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_PASSWORD, e.getMessage());
        }
    }

    /**
     * 새로운 사용자를 등록합니다.
     *
     * @param request 회원가입 요청 정보
     * @return 생성된 사용자 정보
     */
    @Operation(summary = "회원가입", description = "새로운 사용자를 시스템에 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 사용자")
    })
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<?>> registerUser(@RequestBody @Valid RegisterDto request) {
        try {
            Users user = authService.register(request);
            return ResponseUtil.success(ResponseCode.USER_CREATE_SUCCESS, user);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("exists")) {
                return ResponseUtil.fail(ResponseCode.DUPLICATED_USER, e.getMessage());
            }
            return ResponseUtil.fail(ResponseCode.INVALID_PARAMETER, e.getMessage());
        }
    }

    /**
     * 리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.
     *
     * @param request 리프레시 토큰 요청 정보
     * @return 새로운 JWT 토큰
     */
    @Operation(summary = "토큰 갱신", description = "리프레시 토큰을 사용하여 새로운 액세스 토큰과 리프레시 토큰을 발급합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<?>> refresh(@RequestBody @Valid RefreshTokenDto request) {
        try {
            JwtToken jwtToken = authService.refreshToken(request.refreshToken());
            return ResponseUtil.success(ResponseCode.SUCCESS, jwtToken);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_TOKEN, e.getMessage());
        }
    }

    /**
     * 사용자를 로그아웃 처리합니다.
     *
     * @param request 리프레시 토큰 요청 정보
     * @return 응답 DTO
     */
    @Operation(summary = "로그아웃", description = "사용자의 리프레시 토큰을 만료시켜 로그아웃 처리합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰")
    })
    @DeleteMapping("/logout")
    public ResponseEntity<ResponseDto<?>> logout(@RequestBody RefreshTokenDto request) {
        try {
            authService.logout(request.refreshToken());
            return ResponseUtil.success(ResponseCode.LOGOUT_SUCCESS);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_TOKEN, e.getMessage());
        }
    }

    /**
     * Register a new admin user
     * @param request Admin registration request
     * @return Response with created admin user
     */
    @Operation(summary = "관리자 회원가입", description = "새로운 관리자를 시스템에 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 사용자")
    })
    @PostMapping("/admin/signup")
    public ResponseEntity<ResponseDto<?>> registerAdmin(@RequestBody @Valid AdminRegisterDto request) {
        try {
            Users user = authService.registerAdmin(request);
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
    @Operation(summary = "관리자 승인 상태 변경", description = "사용자의 관리자 승인 상태를 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상태 변경 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PutMapping("/admin/approval")
    public ResponseEntity<ResponseDto<?>> updateAdminApproval(@RequestBody @Valid AdminApprovalDto request) {
        try {
            Users user = authService.updateAdminApproval(request);
            return ResponseUtil.success(ResponseCode.SUCCESS, user);
        } catch (RuntimeException e) {
            return ResponseUtil.fail(ResponseCode.INVALID_PARAMETER, e.getMessage());
        }
    }
}