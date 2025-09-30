# Infrastructure 모듈

이 모듈은 데이터베이스 연동, 보안 설정, 외부 서비스 통합 등 기술적인 구현을 담당합니다.

## 📜 개요

`infrastructure` 모듈은 `domain` 모듈에서 정의한 인터페이스의 실제 구현체를 제공합니다. 애플리케이션의 핵심 비즈니스 로직(core)이 구체적인 기술에 종속되지 않도록, 기술과 관련된 모든 세부 사항을 이 모듈에 캡슐화합니다.

## ✨ 주요 기능

- **영속성 계층 구현**: `domain`의 리포지토리 인터페이스를 Spring Data JPA를 사용하여 구현합니다.
  - **JPA Entity**: 데이터베이스 테이블과 매핑되는 `@Entity` 클래스를 정의합니다.
  - **JPA Repository**: Spring Data JPA의 `JpaRepository` 인터페이스를 확장하여 기본적인 CRUD 기능을 구현합니다.
  - **Repository Impl**: `domain`의 리포지토리 인터페이스를 구현한 클래스. JPA Repository를 사용하여 `domain` 모델과 JPA Entity 간의 변환을 책임집니다.
- **보안 설정**: Spring Security를 구성하여 인증(Authentication) 및 인가(Authorization)를 처리합니다. JWT(JSON Web Token)를 사용한 토큰 기반 인증을 구현합니다.
- **외부 서비스 연동**: Redis를 사용한 캐싱, 이메일 발송, 클라우드 스토리지 연동 등 외부 서비스와의 통신을 구현합니다.
- **설정 클래스**: `DataSource`, `JPA`, `Redis`, `Security` 등 각종 기술 관련 설정을 포함합니다.

## 📦 모듈 구조

```
infrastructure/
└── src/
    └── main/
        └── java/io/github/ruifoot/infrastructure/
            ├── config/           # Spring 및 각종 라이브러리 설정
            ├── persistence/      # 영속성 계층 구현
            │   ├── entity/       # JPA 엔티티
            │   ├── mapper/       # 도메인 모델 ↔ JPA 엔티티 변환 매퍼
            │   └── repository/   # Spring Data JPA 리포지토리 및 domain 리포지토리 구현체
            ├── security/         # Spring Security, JWT 관련 구현
            └── service/          # 외부 서비스 연동 구현체 (e.g., S3Service, EmailService)
```

## 🔗 의존성

- **`domain`**: 구현해야 할 리포지토리 인터페이스와 `domain` 모델을 참조합니다.
- **`common`**: 공통 예외 클래스, DTO 등을 사용합니다.

### 주요 라이브러리

- `spring-boot-starter-data-jpa`: 데이터베이스 연동
- `spring-boot-starter-security`: 인증 및 인가
- `spring-boot-starter-data-redis`: Redis 캐싱
- `jjwt`: JWT 토큰 생성 및 검증
- `postgresql`, `mysql-connector-j`: 데이터베이스 드라이버
- `dotenv-java`: `.env` 파일 지원

## 💡 구현 예시

`domain` 모듈의 `TeamRepository`를 `infrastructure` 모듈에서 구현하는 예시입니다.

**`persistence/repository/TeamRepositoryImpl.java`**
```java
@Repository
public class TeamRepositoryImpl implements TeamRepository { // domain의 인터페이스 구현

    private final TeamJpaRepository jpaRepository; // Spring Data JPA 리포지토리
    private final TeamMapper teamMapper;           // 도메인-엔티티 매퍼

    public TeamRepositoryImpl(TeamJpaRepository jpaRepository, TeamMapper teamMapper) {
        this.jpaRepository = jpaRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    public Optional<Team> findById(Long id) {
        return jpaRepository.findById(id)
                            .map(teamMapper::toDomain); // Entity -> Domain 변환
    }

    @Override
    public Team save(Team team) {
        TeamEntity entity = teamMapper.toEntity(team); // Domain -> Entity 변환
        TeamEntity savedEntity = jpaRepository.save(entity);
        return teamMapper.toDomain(savedEntity);      // Entity -> Domain 변환
    }
    // ...
}
```