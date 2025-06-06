# Domain 모듈

이 모듈은 사회인야구 팀 플랫폼의 도메인 모델, 서비스 인터페이스 및 비즈니스 규칙을 포함하고 있습니다.

## 개요

Domain 모듈은 애플리케이션의 핵심 비즈니스 개념과 규칙을 정의합니다. 이 모듈은 기술적 구현 세부사항과 독립적이며, 비즈니스 도메인을 표현하는 데 중점을 둡니다.

## 주요 기능

- 도메인 모델 정의
- 서비스 인터페이스 정의
- 리포지토리 인터페이스 정의
- 비즈니스 규칙 및 제약 조건 정의

## 모듈 구조

```
domain/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/github/ruifoot/domain/
│   │   │       ├── model/          # 도메인 모델/엔티티
│   │   │       ├── repository/     # 리포지토리 인터페이스
│   │   │       └── service/        # 서비스 인터페이스
│   │   └── resources/              # 리소스 파일
│   └── test/                       # 테스트 코드
└── build.gradle                    # 빌드 설정
```

## 의존성

이 모듈은 다음 모듈에 의존합니다:

- **common**: 공통 유틸리티, 응답 형식, 예외 처리 등

이 모듈은 다음 모듈에서 사용됩니다:

- **api**: API 엔드포인트 및 컨트롤러
- **core**: 비즈니스 로직 구현
- **infrastructure**: 데이터베이스, 보안, 외부 서비스 통합

## 도메인 모델 예시

도메인 모델은 비즈니스 개념을 표현하는 클래스입니다. 예를 들어:

```java
package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class Teams extends BaseTimeDomain {
    private long id;
    private String name;
    private LocalDate foundedDate;
    private TeamContact contact;
    private TeamInfo teamInfo;
    private List<TeamHistory> histories;
    private List<TeamRule> rules;
}
```

## 서비스 인터페이스 예시

서비스 인터페이스는 비즈니스 로직의 계약을 정의합니다. 예를 들어:

```java
package io.github.ruifoot.domain.service.baseball;

import io.github.ruifoot.domain.model.baseball.Teams;
import java.util.List;

public interface TeamService {
    Teams findById(Long id);
    List<Teams> findAll();
    Teams create(Teams team);
    Teams update(Long id, Teams team);
    void delete(Long id);
    
    // 특정 비즈니스 기능
    List<Teams> findByRegion(String region);
    void addMember(Long teamId, Long userId);
    void removeMember(Long teamId, Long userId);
}
```

## 리포지토리 인터페이스 예시

리포지토리 인터페이스는 데이터 접근 계약을 정의합니다. 예를 들어:

```java
package io.github.ruifoot.domain.repository;

import io.github.ruifoot.domain.model.baseball.Teams;
import java.util.List;
import java.util.Optional;

public interface TeamRepository {
    Optional<Teams> findById(Long id);
    List<Teams> findAll();
    Teams save(Teams team);
    void delete(Long id);
    
    // 특정 쿼리 메소드
    List<Teams> findByRegion(String region);
    List<Teams> findByNameContaining(String keyword);
}
```

## 설계 원칙

Domain 모듈은 다음 설계 원칙을 따릅니다:

1. **기술 독립성**: 도메인 모델은 특정 기술이나 프레임워크에 의존하지 않습니다.
2. **풍부한 도메인 모델**: 도메인 모델은 데이터뿐만 아니라 비즈니스 로직도 포함합니다.
3. **불변성**: 가능한 한 불변 객체를 사용하여 부작용을 최소화합니다.
4. **명확한 경계**: 도메인 모듈은 명확한 경계와 인터페이스를 가집니다.