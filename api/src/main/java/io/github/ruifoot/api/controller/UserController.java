package io.github.ruifoot.api.controller;


import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ApiResponseUtil;
import io.github.ruifoot.domain.service.users.UserService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "user")
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    @GetMapping("/me")
    public ResponseEntity<?> me(@RequestParam @Email String email) {
        String username = userService.getUsername(email);
        return ApiResponseUtil.success(ResponseCode.SUCCESS,username);
    }


}
