# 프로젝트 기능 개발 TODO 리스트

이 문서는 사회인 야구 플랫폼 API 개발을 위한 기능 요구사항 및 API 엔드포인트를 정의합니다.

---

## 1. 팀 관리 기능

팀 생성부터 멤버 관리까지 팀 운영에 필요한 전반적인 기능을 개발합니다.

### 1.1. 팀 기본 CRUD

- [ ] **팀 생성**
  - `POST /api/v1/teams`
  - **설명:** 새로운 팀을 생성합니다. 요청한 사용자는 자동으로 해당 팀의 '팀장'이 됩니다.
  - **Request Body:** `{ "name": "팀 이름", "region": "활동 지역", "description": "팀 소개" }`

- [ ] **팀 목록 조회 (검색 포함)**
  - `GET /api/v1/teams`
  - **설명:** 전체 팀 목록을 조회합니다. 이름 또는 지역으로 검색 및 페이지네이션 기능을 지원합니다.
  - **Query Params:** `?name={name}&region={region}&page=0&size=10`

- [ ] **특정 팀 상세 정보 조회**
  - `GET /api/v1/teams/{teamId}`
  - **설명:** 특정 팀의 상세 정보(멤버 목록, 전적 등 포함)를 조회합니다.

- [ ] **팀 정보 수정**
  - `PUT /api/v1/teams/{teamId}`
  - **설명:** 팀의 이름, 활동 지역, 소개 등을 수정합니다. (팀 관리자 권한 필요)
  - **Request Body:** `{ "region": "새로운 활동 지역", "description": "수정된 팀 소개" }`

- [ ] **팀 삭제**
  - `DELETE /api/v1/teams/{teamId}`
  - **설명:** 팀을 해체합니다. (팀장만 가능)

### 1.2. 팀 멤버 관리

- [ ] **팀 가입 신청**
  - `POST /api/v1/teams/{teamId}/join-requests`
  - **설명:** 사용자가 특정 팀에 가입 신청을 보냅니다.

- [ ] **팀 가입 신청 목록 조회**
  - `GET /api/v1/teams/{teamId}/join-requests`
  - **설명:** 해당 팀의 가입 신청 목록을 조회합니다. (팀 관리자 권한 필요)

- [ ] **팀 가입 신청 처리 (승인/거절)**
  - `POST /api/v1/teams/{teamId}/join-requests/{requestId}`
  - **설명:** 가입 신청을 승인하거나 거절합니다. (팀 관리자 권한 필요)
  - **Request Body:** `{ "action": "APPROVE" or "REJECT" }`

- [ ] **팀원 목록 조회**
  - `GET /api/v1/teams/{teamId}/members`
  - **설명:** 팀에 소속된 모든 멤버의 목록을 조회합니다.

- [ ] **팀원 역할 변경**
  - `PUT /api/v1/teams/{teamId}/members/{userId}/role`
  - **설명:** 팀원의 역할(예: 선수, 코치)을 변경합니다. (팀 관리자 권한 필요)
  - **Request Body:** `{ "role": "COACH" }`

- [ ] **팀원 방출**
  - `DELETE /api/v1/teams/{teamId}/members/{userId}`
  - **설명:** 팀에서 특정 멤버를 방출합니다. (팀 관리자 권한 필요)

---

## 2. 경기 및 일정 관리 기능

경기를 등록하고 결과를 기록하며, 팀의 경기 일정을 관리합니다.

- [ ] **경기 일정 등록**
  - `POST /api/v1/games`
  - **설명:** 새로운 경기 일정을 등록합니다. (팀 관리자 권한 필요)
  - **Request Body:** `{ "gameDateTime": "2025-10-05T14:00:00", "location": "xx야구장", "homeTeamId": 1, "awayTeamId": 2 }`

- [ ] **팀의 경기 일정 목록 조회**
  - `GET /api/v1/teams/{teamId}/games`
  - **설명:** 특정 팀의 예정되거나 완료된 경기 목록을 조회합니다.
  - **Query Params:** `?status=SCHEDULED` or `FINISHED`

- [ ] **경기 상세 정보 조회**
  - `GET /api/v1/games/{gameId}`
  - **설명:** 특정 경기의 상세 정보(참가 선수, 결과 등)를 조회합니다.

- [ ] **경기 결과 입력**
  - `PUT /api/v1/games/{gameId}/result`
  - **설명:** 경기가 끝난 후 최종 스코어를 입력합니다. (팀 관리자 권한 필요)
  - **Request Body:** `{ "homeScore": 5, "awayScore": 3 }`

- [ ] **경기 취소**
  - `DELETE /api/v1/games/{gameId}`
  - **설명:** 예정된 경기를 취소합니다. (팀 관리자 권한 필요)

---

## 3. 선수 기록/통계 기능

경기 결과를 바탕으로 선수들의 개인 기록을 자동으로 집계하고 조회합니다.

- [ ] **(백엔드) 경기별 선수 기록 입력 로직**
  - **설명:** 경기 결과 입력 시, 각 선수의 상세 기록(타석, 안타, 타점 등)을 함께 저장할 수 있는 로직이 필요합니다. `POST /api/v1/games/{gameId}/player-records` 와 같은 별도 엔드포인트를 고려할 수 있습니다.

- [ ] **(백엔드) 통계 집계 로직 구현**
  - **설명:** 선수 기록이 입력될 때마다 개인별 시즌 통계(타율, 평균자책점 등)를 실시간 또는 배치(Batch)로 집계하여 별도의 통계 테이블에 저장/업데이트합니다.

- [ ] **선수 개인 통계 조회**
  - `GET /api/v1/players/{userId}/stats`
  - **설명:** 특정 선수의 시즌별 통계를 조회합니다.
  - **Query Params:** `?season=2025`

- [ ] **팀내 통계 순위 조회**
  - `GET /api/v1/teams/{teamId}/stats/ranking`
  - **설명:** 팀 내 선수들의 부문별(타율, 홈런 등) 순위를 조회합니다.
  - **Query Params:** `?category=battingAverage`

- [ ] **리그 전체 통계 순위 (리더보드)**
  - `GET /api/v1/stats/leaderboard`
  - **설명:** 리그 전체 선수들의 부문별 순위를 조회합니다.
  - **Query Params:** `?category=homerun&season=2025`

---

## 4. 실시간 알림 기능

주요 이벤트 발생 시 사용자에게 실시간으로 알림을 보냅니다. (SSE 또는 WebSocket 방식 채택 필요)

- [ ] **알림 구독 엔드포인트**
  - `GET /api/v1/notifications/subscribe`
  - **설명:** 클라이언트가 서버로부터 실시간 알림을 받기 위해 연결을 수립합니다. (SSE 방식의 경우)

- [ ] **(백엔드) 알림 생성 및 발송 로직**
  - **설명:** 아래와 같은 이벤트 발생 시 알림을 생성하여 DB에 저장하고, 구독 중인 클라이언트에게 발송합니다.
    - 팀 가입 신청이 왔을 때 (팀 관리자에게)
    - 경기 일정이 등록/변경되었을 때 (팀원들에게)
    - 경기 결과가 등록되었을 때 (팀원들에게)

- [ ] **사용자의 알림 목록 조회**
  - `GET /api/v1/notifications`
  - **설명:** 사용자가 받은 모든 알림 목록을 조회합니다.

- [ ] **알림 읽음 처리**
  - `PUT /api/v1/notifications/{notificationId}/read`
  - **설명:** 사용자가 특정 알림을 읽었음을 서버에 알립니다.
