# Common 모듈

이 모듈은 프로젝트의 모든 모듈에서 공유되는 공통 유틸리티, DTO, 응답 형식, 예외 처리 등을 포함합니다.

## 📜 개요

`common` 모듈은 코드 중복을 최소화하고 프로젝트 전반의 일관성을 유지하는 역할을 합니다. 다른 모듈에 대한 의존성이 없으며, 여러 모듈에서 재사용되는 코드를 중앙에서 관리합니다.

## ✨ 주요 기능

- **표준 API 응답 형식**: `ApiResponse<T>` 클래스를 통해 일관된 응답 구조를 제공합니다.
- **공통 예외 클래스**: `CustomException`, `ResourceNotFoundException` 등 비즈니스 예외를 정의합니다.
- **글로벌 예외 핸들러**: `@RestControllerAdvice`를 사용하여 예외를 공통으로 처리하고 표준 응답을 반환합니다.
- **공통 DTO**: 여러 도메인에서 사용될 수 있는 DTO를 포함합니다. (예: 페이징 요청 DTO)
- **유틸리티 클래스**: 날짜, 문자열 처리 등 범용적인 유틸리티를 제공합니다.

## 📦 모듈 구조

```
common/
└── src/
    └── main/
        └── java/io/github/ruifoot/common/
            ├── dto/           # 공통 데이터 전송 객체
            ├── exception/     # 공통 예외 클래스 및 핸들러
            ├── response/      # 표준 API 응답 클래스
            └── util/          # 유틸리티 클래스
```

## 🔗 의존성

`common` 모듈은 다른 내부 모듈에 의존하지 않는 독립적인 모듈입니다.

### 주요 라이브러리

- `spring-boot-starter-web`: `@RestControllerAdvice` 등 웹 관련 일부 기능 사용
- `lombok`: 보일러플레이트 코드 감소

## 💡 사용 예시

### 표준 응답 반환

```java
import io.github.ruifoot.common.response.ApiResponse;

@GetMapping("/teams/{id}")
public ApiResponse<TeamResponseDto> getTeam(@PathVariable Long id) {
    TeamResponseDto team = teamService.getTeam(id);
    return ApiResponse.success("팀 조회 성공", team);
}
```

### 공통 예외 발생

```java
import io.github.ruifoot.common.exception.ResourceNotFoundException;

public Team getTeamById(Long id) {
    return teamRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
}
```
