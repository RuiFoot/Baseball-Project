# Common 모듈

이 모듈은 사회인야구 팀 플랫폼의 공통 유틸리티, 응답 형식, 예외 처리 등을 포함하고 있습니다.

## 개요

Common 모듈은 프로젝트의 여러 모듈에서 공유되는 공통 코드를 제공합니다. 이 모듈은 코드 중복을 방지하고 일관된 구현을 보장하는 역할을 합니다.

## 주요 기능

- 표준화된 API 응답 형식
- 공통 예외 처리 메커니즘
- 데이터 전송 객체(DTO)
- 유틸리티 클래스

## 모듈 구조

```
common/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/github/ruifoot/common/
│   │   │       ├── dto/           # 공통 데이터 전송 객체
│   │   │       ├── exception/     # 예외 클래스 및 처리
│   │   │       ├── response/      # API 응답 형식
│   │   │       └── util/          # 유틸리티 클래스
│   │   └── resources/             # 리소스 파일
│   └── test/                      # 테스트 코드
└── build.gradle                   # 빌드 설정
```

## 의존성

이 모듈은 다른 모듈에 의존하지 않으며, 다음 모듈에서 사용됩니다:

- **api**: API 엔드포인트 및 컨트롤러
- **core**: 비즈니스 로직 구현
- **domain**: 도메인 모델 및 서비스 인터페이스
- **infrastructure**: 데이터베이스, 보안, 외부 서비스 통합

## 주요 의존성 라이브러리

- Spring Boot Web: 웹 애플리케이션 지원

## 사용 예시

### 표준 응답 형식 사용

```java
import io.github.ruifoot.common.response.ApiResponse;

@RestController
public class ExampleController {
    
    @GetMapping("/example")
    public ApiResponse<String> getExample() {
        return ApiResponse.success("예시 데이터");
    }
    
    @GetMapping("/error-example")
    public ApiResponse<String> getErrorExample() {
        return ApiResponse.error(400, "잘못된 요청입니다.");
    }
}
```

### 예외 처리 사용

```java
import io.github.ruifoot.common.exception.ResourceNotFoundException;

public class ExampleService {
    
    public User findUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        return user;
    }
}
```