# Core 모듈

이 모듈은 애플리케이션의 핵심 비즈니스 로직을 구현합니다.

## 📜 개요

`core` 모듈은 `domain` 모듈에서 정의한 서비스 인터페이스를 실제로 구현하는 역할을 합니다. 도메인 객체를 사용하여 비즈니스 규칙을 수행하고, 데이터 영속성은 `infrastructure` 모듈에 위임합니다. `core` 모듈은 비즈니스 로직의 흐름을 제어하는 데 집중합니다.

## ✨ 주요 기능

- **도메인 서비스 구현**: `domain` 모듈의 서비스 인터페이스(`XxxService`)에 대한 구현체(`XxxServiceImpl`)를 제공합니다.
- **트랜잭션 관리**: `@Transactional` 어노테이션을 통해 비즈니스 로직의 원자성을 보장합니다.
- **비즈니스 규칙 적용**: 도메인 모델과 리포지토리를 사용하여 복잡한 비즈니스 규칙을 처리합니다.
- **다른 서비스와 협력**: 여러 도메인 서비스를 조합하여 하나의 비즈니스 유스케이스를 완성합니다.

## 📦 모듈 구조

```
core/
└── src/
    └── main/
        └── java/io/github/ruifoot/core/
            └── service/       # 비즈니스 로직 서비스 구현체
```

## 🔗 의존성

- **`domain`**: 구현해야 할 서비스 인터페이스와 도메인 모델을 참조합니다.
- **`infrastructure`**: 데이터베이스 접근, 외부 서비스 호출 등 기술적인 구현을 위임합니다.
- **`common`**: 공통 DTO, 예외 클래스 등을 사용합니다.

### 주요 라이브러리

- `spring-security-crypto`: 비밀번호 암호화 등 보안 관련 기능 사용

## 💡 구현 예시

`domain` 모듈에 정의된 `TeamService` 인터페이스를 `core` 모듈에서 구현하는 예시입니다.

**`domain/service/TeamService.java` (인터페이스)**
```java
public interface TeamService {
    Team createTeam(CreateTeamRequest request);
}
```

**`core/service/TeamServiceImpl.java` (구현체)**
```java
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository; // from infrastructure
    private final UserRepository userRepository; // from infrastructure

    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Team createTeam(CreateTeamRequest request) {
        // 1. 사용자 조회
        User owner = userRepository.findById(request.getOwnerId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getOwnerId()));

        // 2. 팀 이름 중복 확인
        if (teamRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Team name already exists");
        }

        // 3. 도메인 모델 생성 및 비즈니스 로직 처리
        Team newTeam = Team.builder()
                           .name(request.getName())
                           .region(request.getRegion())
                           .owner(owner)
                           .build();

        // 4. 데이터 영속화 (infrastructure에 위임)
        return teamRepository.save(newTeam);
    }
}
```
