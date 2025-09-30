# Domain 모듈

이 모듈은 애플리케이션의 핵심 비즈니스 도메인 모델, 서비스 인터페이스, 리포지토리 인터페이스를 정의합니다.

## 📜 개요

`domain` 모듈은 프로젝트의 심장과 같습니다. 이 모듈은 특정 기술에 의존하지 않는 순수한 Java 객체로 비즈니스 개념과 규칙을 표현합니다. 애플리케이션이 "무엇을" 하는지를 정의하며, "어떻게"는 다른 모듈에 위임합니다.

## ✨ 주요 기능

- **도메인 모델 정의**: 비즈니스의 핵심 데이터와 행위를 포함하는 객체(`@Entity`가 없는 순수 POJO 또는 `@Builder` 등을 사용한 풍부한 모델)를 정의합니다.
- **서비스 인터페이스 정의**: 비즈니스 로직의 계약(메서드 시그니처)을 정의합니다. 실제 구현은 `core` 모듈에 있습니다.
- **리포지토리 인터페이스 정의**: 데이터 영속성 계층의 계약을 정의합니다. 실제 구현은 `infrastructure` 모듈에 있습니다.

## 📦 모듈 구조

```
domain/
└── src/
    └── main/
        └── java/io/github/ruifoot/domain/
            ├── model/          # 도메인 모델 (핵심 비즈니스 객체)
            ├── repository/     # 리포지토리 인터페이스
            └── service/        # 서비스 인터페이스
```

## 🔗 의존성

- **`common`**: 여러 도메인에서 공통으로 사용될 수 있는 일부 DTO나 유틸리티를 참조할 수 있습니다. 그 외 다른 내부 모듈에 대한 의존성이 없습니다.

## 💡 설계 원칙

- **기술 독립성**: `domain` 모듈은 Spring Data JPA, Spring Security 등 특정 프레임워크나 기술에 의존하지 않습니다. 이를 통해 비즈니스 로직의 테스트 용이성과 유연성을 확보합니다.
- **풍부한 도메인 모델 (Rich Domain Model)**: 도메인 객체는 단순한 데이터 덩어리(Anemic Domain Model)가 아니라, 관련 비즈니스 로직을 스스로 포함하도록 설계하는 것을 지향합니다.
- **인터페이스 기반 설계**: 서비스와 리포지토리는 인터페이스로 정의하여 구현체와 분리(DIP)함으로써, 각 계층의 역할을 명확히 하고 교체 가능성을 높입니다.

## 💡 구현 예시

### 도메인 모델

```java
// package io.github.ruifoot.domain.model.team;

@Getter
@Builder
public class Team {
    private Long id;
    private String name;
    private String region;
    private User owner;
    private List<Player> players;

    // Team과 관련된 비즈니스 로직 (예: 팀원 추가)
    public void addPlayer(Player player) {
        if (this.players.contains(player)) {
            throw new IllegalArgumentException("Player is already in the team.");
        }
        this.players.add(player);
    }
}
```

### 서비스 인터페이스

```java
// package io.github.ruifoot.domain.service;

public interface TeamService {
    Team createTeam(CreateTeamRequest request);
    Team findTeamById(Long teamId);
    void addPlayerToTeam(Long teamId, Long playerId);
}
```

### 리포지토리 인터페이스

```java
// package io.github.ruifoot.domain.repository;

public interface TeamRepository {
    Optional<Team> findById(Long id);
    boolean existsByName(String name);
    Team save(Team team);
}
```
