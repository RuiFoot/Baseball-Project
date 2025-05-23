# 사회인야구 팀 플랫폼 API

이 프로젝트는 사회인야구 팀을 위한 플랫폼 API를 제공합니다. 팀 관리, 경기 일정 등의 기능을 RESTful API로 제공합니다.

## 프로젝트 구조

```
Baseball-Project/
├── api              # API 엔드포인트 및 컨트롤러
├── common           # 공통 유틸리티, 응답 형식, 예외 처리
├── core             # 비즈니스 로직 구현
├── domain           # 도메인 모델 및 서비스 인터페이스
└── infrastructure   # 데이터베이스, 보안, 외부 서비스 통합
```

### 모듈 설명

- **api**: REST API 엔드포인트와 컨트롤러를 포함합니다. 요청 검증 및 응답 형식화를 담당합니다.
- **common**: 모든 모듈에서 공유하는 공통 코드, 유틸리티, DTO, 예외 처리 등을 포함합니다.
- **core**: 비즈니스 로직 구현체를 포함합니다. 도메인 서비스 인터페이스를 구현합니다.
- **domain**: 도메인 모델, 엔티티, 서비스 인터페이스를 포함합니다. 비즈니스 규칙을 정의합니다.
- **infrastructure**: 데이터베이스 접근, 보안, 외부 서비스 통합 등 기술적 구현을 담당합니다.

## 기술 스택

- **언어**: Java 21
- **프레임워크**: Spring Boot 3.4.5
- **보안**: Spring Security, JWT
- **데이터베이스**: JPA, PostgreSQL
- **캐싱**: Redis
- **문서화**: SpringDoc OpenAPI
- **빌드 도구**: Gradle
- **기타**: Lombok

## API 엔드포인트

테스트용 API 엔드포인트:

### 인증 API

- `GET /auth/me` - 현재 로그인한 사용자 정보 조회
- `POST /auth/login` - 사용자 로그인
- `POST /auth/signup` - 사용자 회원가입
- `DELETE /auth/logout` - 사용자 로그아웃


현재 구현된 API 엔드포인트:

### 인증 API

- `---`

## 시작하기

### 필수 조건

- Java 21
- Gradle
-  PostgreSQL
- Redis (선택사항)

### 설치 및 실행

1. 저장소 클론
   ```bash
   git clone https://github.com/ruifoot/Baseball-Project.git
   cd Baseball-Project
   ```

2. 환경 변수 설정
   `.env` 파일을 프로젝트 루트에 생성하고 필요한 환경 변수를 설정합니다.
   ```
   DB_URL=jdbc:mysql://localhost:3306/baseball
   DB_USERNAME=root
   DB_PASSWORD=password
   JWT_SECRET=your_jwt_secret_key
   ```

3. 빌드 및 실행
   ```bash
   ./gradlew clean build
   ./gradlew :api:bootRun
   ```

4. API 문서 접근
   애플리케이션이 실행되면 다음 URL에서 API 문서에 접근할 수 있습니다:
   ```
   http://localhost:8080/swagger-ui.html
   ```

## 응답 형식

모든 API 응답은 다음과 같은 표준 형식을 따릅니다:

```json
{
  "status": 200,
  "message": "요청이 성공적으로 처리되었습니다.",
  "data": {}
}
```

- `status`: HTTP 상태 코드
- `message`: 응답 메시지
- `data`: 응답 데이터 (선택사항)

## 예외 처리

애플리케이션은 다음과 같은 예외를 처리합니다:

- 유효성 검증 오류 (400)
- 인증 오류 (401)
- 권한 오류 (403)
- 리소스 없음 (404)
- 중복 리소스 (409)
- 서버 내부 오류 (500)

## 개발 예정인 기능

- 팀 관리 API
- 경기 일정 관리 API
- 선수 통계 API
- 실시간 알림
