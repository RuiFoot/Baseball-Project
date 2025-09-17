package io.github.ruifoot.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 경기 및 일정 관리 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/games") // API 버전 명시
public class GameController {

    /*
    TODO[GameController]: 경기 일정 목록 조회 (GET /)
     - 프론트엔드 경기 일정 페이지(/schedule)용 API
     - Query Params: ?year=2024&month=6&status=upcoming (연도, 월, 상태별 필터링)
     - Response: List<GameSummaryDto>
    */

    /*
    TODO[GameController]: 경기 상세 정보 조회 (GET /{gameId})
     - 프론트엔드 경기 상세 페이지(/games/[id])용 API
     - PathVariable: gameId
     - Response: GameDetailDto (기본정보, 날씨, 선발투수, 라인업, 결과 등)
    */

    /*
    TODO[GameController]: 경기 스코어보드 조회 (GET /{gameId}/scoreboard)
     - 경기 상세 페이지의 '스코어보드' 탭 데이터
     - Response: ScoreboardDto (팀별 이닝별 득점, R, H, E 정보 포함)
    */

    /*
    TODO[GameController]: 경기 타격 기록 조회 (GET /{gameId}/batting-stats)
     - 경기 상세 페이지의 '타격 기록' 탭 데이터
     - Response: List<GameBattingStatDto> (선수별 타수, 안타, 타점 등)
    */

    /*
    TODO[GameController]: 경기 투구 기록 조회 (GET /{gameId}/pitching-stats)
     - 경기 상세 페이지의 '투구 기록' 탭 데이터
     - Response: List<GamePitchingStatDto> (투수별 이닝, 실점, 자책점 등)
    */

    /*
    TODO[GameController]: 경기 일정 등록 (POST /)
     - Admin 권한 필요
     - RequestBody: { "gameDateTime": "2025-10-05T14:00:00", "location": "xx야구장", "homeTeamId": 1, "awayTeamId": 2 }
    */

    /*
    TODO[GameController]: 경기 결과 입력 (PUT /{gameId}/result)
     - Admin 권한 필요
     - PathVariable: gameId
     - RequestBody: { "homeScore": 5, "awayScore": 3 }
     - 이 API 호출 시, 선수별 상세 기록(player-records)도 함께 처리하거나, 별도 API로 분리해야 함.
    */

    /*
    TODO[GameController]: 경기별 선수 기록 입력 (POST /{gameId}/player-records)
     - Admin 권한 필요
     - PathVariable: gameId
     - RequestBody: List<PlayerRecordDto> (타자별 타석, 안타, 타점 등, 투수별 이닝, 자책점 등)
     - 경기 결과 입력 후, 이 API를 통해 상세 기록을 입력받아 통계를 집계하는 흐름.
    */

    /*
    TODO[GameController]: 경기 취소 (DELETE /{gameId})
     - Admin 권한 필요
     - PathVariable: gameId
    */
}