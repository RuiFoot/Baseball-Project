package io.github.ruifoot.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 선수 기록 및 통계 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stats") // API 버전 명시
public class StatsController {

    /*
    TODO[StatsController]: 팀 전체 기록 개요 조회 (GET /team-overview)
     - 프론트엔드 기록실(/stats) 개요 탭용 API
     - QueryParams: ?season=2024
     - Response: TeamStatsDto (팀성적, 팀타격, 팀투구 정보 포함)
    */

    /*
    TODO[StatsController]: 타격 부문 순위 (리더보드) 조회 (GET /batting-leaders)
     - 프론트엔드 기록실(/stats) 개요 탭 및 타격 기록 탭용 API
     - QueryParams: ?season=2024&category=battingAverage&limit=5 (타율, 홈런, 타점 등)
     - Response: List<PlayerBattingStatDto>
    */

    /*
    TODO[StatsController]: 투구 부문 순위 (리더보드) 조회 (GET /pitching-leaders)
     - 프론트엔드 기록실(/stats) 개요 탭 및 투구 기록 탭용 API
     - QueryParams: ?season=2024&category=era&limit=5 (방어율, 다승, 탈삼진 등)
     - Response: List<PlayerPitchingStatDto>
    */

    /*
    TODO[StatsController]: (백엔드) 통계 집계 로직 구현
     - 경기별 선수 기록이 입력될 때마다 개인별 시즌 통계(타율, 평균자책점 등)를 실시간 또는 배치(Batch)로 집계하여 별도의 통계 테이블에 저장/업데이트합니다.
     - 이 로직이 선행되어야 위 조회 API들이 정상 동작할 수 있습니다.
    */
}