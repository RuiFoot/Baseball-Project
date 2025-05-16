package io.github.ruifoot.api.controller;


import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ApiResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "auth")
public class AuthController {



    @GetMapping("/auth/me")
    public ResponseEntity<CommonResponseDto<?>> me() {
        return ApiResponseUtil.success(ResponseCode.SUCCESS);
    }
}
