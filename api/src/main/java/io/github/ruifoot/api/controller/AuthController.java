package io.github.ruifoot.api.controller;


import io.github.ruifoot.api.dto.auth.request.LoginRequest;
import io.github.ruifoot.api.dto.auth.request.SignupRequest;
import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ApiResponseUtil;
import io.github.ruifoot.domain.service.users.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "auth")
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<CommonResponseDto<?>> me() {
        String username = userService.getUsername(1L);
        return ApiResponseUtil.success(ResponseCode.SUCCESS,username);
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponseDto<?>> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponseUtil.success(ResponseCode.SUCCESS, request);
    }
    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<?>> signup(@RequestBody @Valid SignupRequest request) {
        return ApiResponseUtil.success(ResponseCode.SUCCESS);
    }
    @DeleteMapping("/logout")
    public ResponseEntity<CommonResponseDto<?>> logout() {
        return ApiResponseUtil.success(ResponseCode.SUCCESS);
    }


}
