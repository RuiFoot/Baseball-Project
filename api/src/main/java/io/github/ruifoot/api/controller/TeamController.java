package io.github.ruifoot.api.controller;


import io.github.ruifoot.domain.service.baseball.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 팀 정보 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams") // API 버전 명시
public class TeamController {
    private final TeamService teamService;

    /*
    TODO[TeamController]: 팀 생성 (POST /)
     - RequestBody: { "name": "팀 이름", "region": "활동 지역", "description": "팀 소개" }
     - 요청한 사용자를 자동으로 해당 팀의 '팀장'으로 설정
    */

    /*
    TODO[TeamController]: 팀 목록 조회 (GET /)
     - QueryParams: ?name={name}&region={region}&page=0&size=10
     - Response: Paged<TeamSummaryDto>
    */

    /*
    TODO[TeamController]: 특정 팀 상세 정보 조회 (GET /{teamId})
     - 프론트엔드 팀 페이지(/team)용 API
     - PathVariable: teamId
     - Response: TeamDetailDto (팀 기본정보, 팀원 목록, 팀 연혁, 팀 규칙 등 포함)
    */

    /*
    TODO[TeamController]: 팀 정보 수정 (PUT /{teamId})
     - 팀 관리자(팀장, 총무 등) 권한 필요
     - PathVariable: teamId
     - RequestBody: UpdateTeamInfoDto
    */

    /*
    TODO[TeamController]: 팀 삭제 (DELETE /{teamId})
     - 팀장만 가능
     - PathVariable: teamId
    */

    /*
    TODO[TeamController]: 팀 가입 신청 (POST /{teamId}/join-requests)
     - 로그인한 사용자
     - PathVariable: teamId
    */

    /*
    TODO[TeamController]: 팀 가입 신청 목록 조회 (GET /{teamId}/join-requests)
     - 팀 관리자 권한 필요
     - PathVariable: teamId
    */

    /*
    TODO[TeamController]: 팀 가입 신청 처리 (POST /{teamId}/join-requests/{requestId})
     - 팀 관리자 권한 필요
     - PathVariable: teamId, requestId
     - RequestBody: { "action": "APPROVE" or "REJECT" }
    */

    /*
    TODO[TeamController]: 팀원 목록 조회 (GET /{teamId}/members)
     - PathVariable: teamId
     - Response: List<TeamMemberDto>
    */

    /*
    TODO[TeamController]: 팀원 역할 변경 (PUT /{teamId}/members/{userId}/role)
     - 팀 관리자 권한 필요
     - PathVariable: teamId, userId
     - RequestBody: { "role": "COACH" }
    */

    /*
    TODO[TeamController]: 팀원 방출 (DELETE /{teamId}/members/{userId})
     - 팀 관리자 권한 필요
     - PathVariable: teamId, userId
    */
}