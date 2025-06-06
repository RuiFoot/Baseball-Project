package io.github.ruifoot.api.controller;


import io.github.ruifoot.domain.service.baseball.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 팀 정보 관련 컨트롤러
 */
@RestController(value = "team")
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;


}
