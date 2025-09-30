# Infrastructure 모듈 테스트

이 디렉토리는 `infrastructure` 모듈의 통합 테스트 코드를 포함합니다.

## 📜 개요

인프라스트럭처 모듈의 테스트는 데이터베이스 연동, 보안 설정, 외부 API 연동 등 기술적인 컴포넌트들이 올바르게 동작하는지 검증하는 데 중점을 둡니다. 주로 Spring Boot의 테스트 지원 기능을 사용하여 실제와 유사한 환경에서 테스트를 진행합니다.

## ✅ 테스트 범위

- **영속성 계층 테스트 (`/persistence`)**: Spring Data JPA를 사용한 리포지토리 구현체의 동작을 검증합니다.
  - `@DataJpaTest`를 사용하여 실제 데이터베이스(테스트용 H2)와 상호작용하며 CRUD 기능 및 쿼리 메소드를 테스트합니다.
- **보안 테스트 (`/security`)**: JWT 발급, 검증 및 Spring Security 필터 체인 동작을 검증합니다.
  - `@SpringBootTest`와 `MockMvc`를 사용하여 실제 보안 필터가 적용된 상태에서 API 접근 제어를 테스트합니다.
- **설정 클래스 테스트 (`/config`)**: 각종 `@Configuration` 클래스가 정상적으로 로드되고 Bean을 생성하는지 확인합니다.

## 🛠️ 테스트 환경

- **In-Memory Database**: 테스트 실행 시 H2 인메모리 데이터베이스를 사용하여 외부 데이터베이스 의존성을 제거하고 격리된 환경을 제공합니다.
- **Test Profile**: `src/test/resources/application.yml` 에 테스트 전용 설정을 정의하여 사용합니다.

## 🚀 테스트 실행

아래 명령어를 통해 `infrastructure` 모듈의 모든 테스트를 실행할 수 있습니다.

```bash
./gradlew :infrastructure:test
```

JaCoCo 플러그인이 적용되어 있어 테스트 실행 후 코드 커버리지 리포트를 생성할 수 있습니다.

```bash
# 테스트 실행 및 커버리지 리포트 생성
./gradlew :infrastructure:jacocoTestReport

# 리포트 확인 (HTML)
# build/reports/jacoco/test/html/index.html
```

## 💡 새 테스트 작성 가이드

1.  **테스트 클래스 위치**: 테스트하려는 소스 코드와 동일한 패키지 구조를 따라 `src/test/java` 내에 생성합니다.
2.  **테스트 유형에 맞는 어노테이션 사용**:
    - JPA 리포지토리 테스트: `@DataJpaTest`
    - 웹 계층(Controller, Filter)을 포함한 통합 테스트: `@SpringBootTest`, `@AutoConfigureMockMvc`
    - 특정 Bean만 필요한 경우: `@ExtendWith(SpringExtension.class)`, `@ContextConfiguration`
3.  **Given-When-Then 패턴**: 테스트 코드의 가독성을 높이기 위해 given-when-then 구조를 따르는 것을 권장합니다.
4.  **테스트 격리**: 각 테스트는 서로 영향을 주지 않도록 `@Transactional`을 사용하거나, `@BeforeEach` / `@AfterEach`를 통해 상태를 초기화합니다.