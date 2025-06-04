# 인프라스트럭처 모듈

이 모듈은 사회인야구 팀 플랫폼의 인프라스트럭처 컴포넌트를 포함하고 있습니다. 데이터베이스 접근, 보안, 외부 서비스 통합 등 기술적 구현을 담당합니다.

## 개요

인프라스트럭처 모듈은 애플리케이션의 기술적 세부 사항을 구현합니다. 이 모듈은 도메인 모델과 비즈니스 로직을 기술적 인프라와 연결하는 역할을 합니다.

## 주요 기능

- **영속성 계층**: JPA 엔티티, 리포지토리 구현
- **보안 설정**: Spring Security 구성
- **인증**: JWT 기반 인증 메커니즘
- **캐싱**: Redis를 사용한 데이터 캐싱
- **매퍼**: 도메인 모델과 JPA 엔티티 간 변환

## 모듈 구조

```
infrastructure/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/github/ruifoot/infrastructure/
│   │   │       ├── config/           # 구성 클래스
│   │   │       ├── persistence/      # 영속성 관련 클래스
│   │   │       │   ├── entity/       # JPA 엔티티
│   │   │       │   ├── repository/   # 리포지토리 구현
│   │   │       │   └── mapper/       # 엔티티-도메인 매퍼
│   │   │       ├── security/         # 보안 관련 클래스
│   │   │       └── service/          # 인프라 서비스
│   │   └── resources/                # 리소스 파일
│   └── test/                         # 테스트 코드
└── build.gradle                      # 빌드 설정
```

## 의존성

이 모듈은 다음 모듈에 의존합니다:

- **domain**: 도메인 모델 및 서비스 인터페이스

이 모듈은 다음 모듈에서 사용됩니다:

- **core**: 비즈니스 로직 구현

## 주요 의존성 라이브러리

- Spring Data JPA: 데이터 접근 계층
- Spring Security: 인증 및 권한 부여
- JWT: 토큰 기반 인증
- Redis: 캐싱
- PostgreSQL: 관계형 데이터베이스

## 매퍼 사용 예시

인프라스트럭처 모듈은 도메인 모델과 JPA 엔티티 간의 변환을 위한 매퍼를 제공합니다:

```java
@Component
public class UserMapper implements EntityMapper<Users, io.github.ruifoot.domain.model.user.Users> {

    @Override
    public io.github.ruifoot.domain.model.user.Users toDomain(Users entity) {
        if (entity == null) {
            return null;
        }

        io.github.ruifoot.domain.model.user.Users domain = new io.github.ruifoot.domain.model.user.Users();
        domain.setId(entity.getId() != null ? entity.getId() : 0);
        domain.setUsername(entity.getUsername());
        domain.setEmail(entity.getEmail());
        domain.setPasswordHash(entity.getPasswordHash());
        domain.setRole(entity.getRole());
        domain.setCreatedAt(entity.getCreatedAt());
        domain.setUpdatedAt(entity.getUpdatedAt());

        return domain;
    }

    @Override
    public Users toEntity(io.github.ruifoot.domain.model.user.Users domain) {
        // 구현 생략
    }

    @Override
    public Users updateEntityFromDomain(Users entity, io.github.ruifoot.domain.model.user.Users domain) {
        // 구현 생략
    }
}
```

## 리포지토리 구현 예시

인프라스트럭처 모듈은 도메인 리포지토리 인터페이스의 구현을 제공합니다:

```java
@Repository
public class JpaUserRepository implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Autowired
    public JpaUserRepository(UserJpaRepository userJpaRepository, UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userMapper = userMapper;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id)
            .map(userMapper::toDomain);
    }

    @Override
    public User save(User user) {
        Users entity = userMapper.toEntity(user);
        Users savedEntity = userJpaRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }
}
```

## 테스트

인프라스트럭처 모듈은 JaCoCo를 사용한 코드 커버리지 분석을 포함한 포괄적인 테스트 스위트를 제공합니다. 테스트 실행 및 코드 커버리지 분석에 관한 자세한 정보는 [TESTING.md](TESTING.md) 파일을 참조하세요.
