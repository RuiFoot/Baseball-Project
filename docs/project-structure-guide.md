# Baseball Project 구조 가이드 (v2 - 실제 구조 기반)

## 1. 프로젝트 개요

본 프로젝트는 야구 게임 애플리케이션으로, Spring Boot를 사용하여 개발되었습니다. Java 21과 Gradle 8.5 버전을 사용하고 있으며, 각기 다른 책임을 가진 여러 모듈로 구성된 멀티 모듈(Multi-module) 프로젝트입니다.

**주요 기술 스택:**

*   **언어:** Java 21
*   **프레임워크:** Spring Boot 3.2.5
*   **빌드 도구:** Gradle 8.5
*   **데이터베이스:** 외부 데이터베이스 사용 (MySQL, PostgreSQL 등)
*   **주요 라이브러리:**
    *   Spring Data JPA, Spring Data Redis
    *   Spring Security, JJWT (JWT 기반 인증)
    *   Lombok, SpringDoc (Swagger UI)

## 2. 시작 가이드 (Getting Started)

1.  **저장소 복제 (Clone)**
    ```bash
    git clone [저장소 URL]
    ```
2.  **IDE로 프로젝트 열기**
    *   IntelliJ IDEA에서 Gradle 프로젝트로 엽니다.
3.  **데이터베이스 연결 설정**
    *   `infrastructure/src/main/resources/application-local.yml` 파일에 로컬 개발 환경에서 접속할 데이터베이스 정보를 입력합니다.
4.  **애플리케이션 실행**
    *   `api` 모듈의 `ApiApplication.java`를 실행합니다.
    *   IDE 실행 설정에서 `spring.profiles.active=local`을 추가하여 로컬 프로파일을 활성화합니다.

## 3. 프로젝트 아키텍처 (실제 구조)

`build.gradle` 파일 분석 결과, 본 프로젝트는 일반적인 계층형 아키텍처와는 다른, 독자적인 의존성 구조를 가집니다. 특히 `core` 모듈이 여러 모듈의 기능을 조합하는 중심 허브 역할을 수행하는 것으로 보입니다.

### 3.1. 모듈 의존 관계 (실제)

```text
[api]
  |--> [core] (실질적인 로직 처리)
  |--> [common] (DTO 등 공통 데이터)
  |--> [domain] (컴파일 시 타입 참조용)

[core]
  |--> [infrastructure] (DB, Redis 등 외부 시스템 접근)
  |--> [domain] (핵심 비즈니스 모델 참조)
  |--> [common] (공통 데이터)

[infrastructure]
  |--> [domain] (Repository 인터페이스 구현을 위해)
  |--> [common] (공통 데이터)

[domain]
  |--> [common] (공통 데이터)

[common]
  (의존성 없음)
```

### 3.2. 모듈별 상세 설명 (실제 역할 기반)

#### `api` (Application Layer)
*   **역할:** 외부 HTTP 요청을 수신하고 응답하는 진입점입니다. 실제 비즈니스 처리는 대부분 `core` 모듈에 위임합니다.
*   **의존성:** `core`, `common`, `domain`

#### `core` (Orchestration/Service Layer)
*   **역할:** 애플리케이션의 핵심 서비스 로직을 총괄하는 **중심 허브(Hub)** 모듈입니다. `domain`의 비즈니스 모델과 `infrastructure`의 외부 시스템 접근 기능을 조합하여 실제 서비스를 구현합니다.
*   **의존성:** `infrastructure`, `domain`, `common`

#### `infrastructure` (Infrastructure Layer)
*   **역할:** 데이터베이스(JPA), Redis, 외부 API 연동 등 외부 시스템과의 통신을 실제로 구현하는 계층입니다. `domain`에 정의된 Repository 인터페이스의 구현체를 제공합니다.
*   **의존성:** `domain`, `common`

#### `domain` (Domain Model Layer)
*   **역할:** 애플리케이션의 가장 핵심적인 데이터 모델(`@Entity`)과 비즈니스 규칙, 그리고 데이터 접근을 위한 인터페이스(`Repository`)를 정의합니다. 실제 구현은 포함하지 않습니다.
*   **의존성:** `common`

#### `common` (Common Utilities)
*   **역할:** DTO, 공통 예외, 상수 등 프로젝트 전반에서 사용되는 가장 기본적인 요소들을 포함하는 최하위 모듈입니다.
*   **의존성:** 없음

## 4. 빌드 및 실행

*   **전체 빌드:** `./gradlew clean build`
*   **실행:** `java -jar -Dspring.profiles.active=local api/build/libs/api-0.0.1-SNAPSHOT.jar`

## 5. 테스트 및 코드 커버리지

*   **전체 테스트 실행:** `./gradlew test`
*   **커버리지 리포트 확인:** `./gradlew testWithCoverage` 명령어를 실행하면 콘솔과 `build/jacocoHtml` 폴더에서 커버리지 리포트를 확인할 수 있습니다.

(이하 내용은 이전과 동일하게 유지됩니다: 코딩 컨벤션, API 문서화, 데이터베이스 관리, 설정 관리, 형상 관리 등)