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
 * 유저 관련 기능을 위한 컨트롤러
 * 사용자 프로필 조회, 개인정보 수정 등 활용 예정
 */
@RestController(value = "user")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    // TODO 토큰으로 유저를 찾을 수 있게 처리하기
    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestParam @Email String email) {
        String username = userService.getUsername(email);
        return ResponseUtil.success(ResponseCode.SUCCESS,username);
    }

    /*
    TODO[UserController]: 사용자 프로필 조회, 개인정보 수정
     */

}
