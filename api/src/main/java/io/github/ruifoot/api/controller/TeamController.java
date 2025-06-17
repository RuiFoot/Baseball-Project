package io.github.ruifoot.api.controller;


import io.github.ruifoot.common.dto.ResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ResponseUtil;
import io.github.ruifoot.domain.service.baseball.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 팀 정보 관련 컨트롤러
 */
@RestController(value = "team")
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<?>> me() {

        return ResponseUtil.success(ResponseCode.SUCCESS,"data");
    }
    @PostMapping("/me")
    public ResponseEntity<ResponseDto<?>> createMe(){
        return ResponseUtil.success(ResponseCode.SUCCESS,"data");
    }
    @PutMapping("/me")
    public ResponseEntity<ResponseDto<?>> updateMe(){
        return ResponseUtil.success(ResponseCode.SUCCESS,"data");
    }

}
