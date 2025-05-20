package io.github.ruifoot.api.controller;


import io.github.ruifoot.api.dto.auth.request.LoginRequest;
import io.github.ruifoot.api.dto.auth.request.SignupRequest;
import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "auth")
@RequestMapping("/auth")
public class AuthController {



    @GetMapping("/me")
    public ResponseEntity<CommonResponseDto<?>> me() {
        return ApiResponseUtil.success(ResponseCode.SUCCESS);
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
