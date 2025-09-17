package io.github.ruifoot.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 선수 정보 및 기록 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/players")
public class PlayerController {
    // private final PlayerService playerService;

    /*
    TODO[PlayerController]: 선수 명단 조회 (GET /)
     - 프론트엔드 선수 명단 페이지(/players)에 필요한 기본 정보 목록 반환
     - Query Params: ?position=투수 (포지션별 필터링)
     - Response: List<PlayerCardDto> (id, name, number, position, role, profileImage, 주요스탯)
    */

    /*
    TODO[PlayerController]: 선수 상세 정보 조회 (GET /{playerId})
     - 프론트엔드 선수 상세 페이지(/players/[id])에 필요한 모든 정보 반환
     - PathVariable: playerId
     - Response: PlayerDetailDto (기본정보, 시즌기록(타격/투구/수비), 수상경력 등)
    */

    /*
    TODO[PlayerController]: 선수 시즌별 통계 조회 (GET /{playerId}/stats)
     - Query Params: ?season=2024
     - 특정 선수의 특정 시즌 기록을 반환
     - Response: PlayerStatsDto
    */

    /*
    TODO[PlayerController]: 선수 경기별 기록 조회 (GET /{playerId}/gamelog)
     - 프론트엔드 '경기별 기록' 탭에 필요한 데이터
     - Query Params: ?season=2024&page=0&size=10 (페이지네이션)
     - Response: Paged<PlayerGameLogDto>
    */

    /*
    TODO[PlayerController]: 선수 정보 등록 (POST /)
     - Admin 권한 필요
     - RequestBody: CreatePlayerRequest
    */

    /*
    TODO[PlayerController]: 선수 정보 수정 (PUT /{playerId})
     - Admin 권한 또는 본인 확인
     - RequestBody: UpdatePlayerRequest
    */
}
