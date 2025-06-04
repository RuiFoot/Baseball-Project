# API 모듈

이 모듈은 사회인야구 팀 플랫폼의 REST API 엔드포인트와 컨트롤러를 포함하고 있습니다.

## 개요

API 모듈은 클라이언트 애플리케이션과의 통신을 담당하는 진입점입니다. 이 모듈은 HTTP 요청을 받아 처리하고, 적절한 응답을 반환합니다.

## 주요 기능

- REST API 엔드포인트 제공
- 요청 데이터 검증
- 응답 데이터 형식화
- API 문서화 (Swagger/OpenAPI)

## 모듈 구조

```
api/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/github/ruifoot/api/
│   │   │       ├── controller/    # REST 컨트롤러
│   │   │       ├── dto/           # 데이터 전송 객체
│   │   │       └── ApiApplication.java  # 애플리케이션 진입점
│   │   └── resources/
│   │       ├── application.yml    # 애플리케이션 설정
│   │       └── static/            # 정적 리소스
│   └── test/                      # 테스트 코드
└── build.gradle                   # 빌드 설정
```

## 의존성

이 모듈은 다음 모듈에 의존합니다:

- **common**: 공통 유틸리티, 응답 형식, 예외 처리 등
- **core**: 비즈니스 로직 구현
- **domain**: 도메인 모델 및 서비스 인터페이스 (컴파일 타임 의존성)

## 주요 의존성 라이브러리

- Spring Boot Web: REST API 구현
- Spring Boot Actuator: 애플리케이션 모니터링 및 관리
- dotenv-java: 환경 변수 관리

## 실행 방법

API 모듈은 애플리케이션의 진입점으로, 다음 명령어로 실행할 수 있습니다:

```bash
./gradlew :api:bootRun
```

## API 문서

애플리케이션이 실행되면 다음 URL에서 API 문서에 접근할 수 있습니다:

```
http://localhost:8080/swagger-ui.html
```