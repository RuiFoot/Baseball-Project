# API 모듈

이 모듈은 사회인 야구 팀 플랫폼의 REST API 엔드포인트와 컨트롤러를 포함하고 있습니다.

## 📜 개요

`api` 모듈은 클라이언트 애플리케이션과의 HTTP 통신을 담당하는 진입점입니다. 요청을 받아 비즈니스 로직을 호출하고, 결과를 표준화된 응답 형식으로 반환합니다.

## ✨ 주요 기능

- RESTful API 엔드포인트 제공 (Controller)
- 요청 데이터 유효성 검사 (DTO, `@Valid`)
- 인증 및 인가 처리 (Spring Security 연동)
- API 문서 자동화 (SpringDoc OpenAPI)

## 📦 모듈 구조

```
api/
└── src/
    └── main/
        ├── java/io/github/ruifoot/api/
        │   ├── controller/    # REST 컨트롤러
        │   ├── dto/           # 데이터 전송 객체 (요청/응답)
        │   └── ApiApplication.java  # Spring Boot 애플리케이션 진입점
        └── resources/
            ├── application.yml    # 애플리케이션 설정
            └── .env               # 환경 변수 파일
```

## 🔗 의존성

- **`common`**: 공통 DTO, 예외 처리, 응답 형식 등
- **`core`**: 핵심 비즈니스 로직 호출
- **`domain`**: 도메인 모델 (컴파일 시점에만 의존)

### 주요 라이브러리

- `spring-boot-starter-web`: REST API 구현
- `spring-boot-starter-thymeleaf`: (필요 시) 간단한 웹 페이지 템플릿
- `spring-boot-starter-actuator`: 애플리케이션 모니터링
- `dotenv-java`: `.env` 파일을 이용한 환경 변수 관리
- `springdoc-openapi-starter-webmvc-ui`: API 문서 자동화

## 🚀 실행 방법

`api` 모듈은 애플리케이션의 메인 진입점이므로, 아래 명령어로 전체 애플리케이션을 실행할 수 있습니다.

```bash
./gradlew :api:bootRun
```

실행 후 `http://localhost:8080/swagger-ui.html` 에서 API 문서를 확인할 수 있습니다.
