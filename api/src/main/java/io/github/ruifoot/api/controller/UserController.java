package io.github.ruifoot.api.controller;


import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ResponseUtil;
import io.github.ruifoot.domain.service.user.UserService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 로그인한 사용자 관련 기능을 위한 컨트롤러
 * (내 정보, 프로필 수정 등)
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users") // API 버전 명시
public class UserController {

    private final UserService userService;

    // TODO: Spring Security Context에서 인증된 사용자 정보 가져오도록 수정
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        // 예시: SecurityContextHolder.getContext().getAuthentication().getName()
        String currentUsername = "testuser"; // 임시
        // Users user = userService.getUserByUsername(currentUsername);
        // return ResponseUtil.success(ResponseCode.SUCCESS, user);
        return ResponseUtil.success(ResponseCode.SUCCESS, currentUsername);
    }

    /*
    TODO[UserController]: 내 프로필 정보 조회 (GET /me/profile)
     - Response: UserProfileDto
     */

    /*
    TODO[UserController]: 내 프로필 정보 수정 (PUT /me/profile)
     - RequestBody: UpdateProfileRequest
     */

    /*
    TODO[UserController]: 내 비밀번호 변경 (PUT /me/password)
     - RequestBody: { "currentPassword": "...", "newPassword": "..." }
     */
}