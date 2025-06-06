# Core 모듈

이 모듈은 사회인야구 팀 플랫폼의 비즈니스 로직 구현을 포함하고 있습니다.

## 개요

Core 모듈은 애플리케이션의 핵심 비즈니스 로직을 구현합니다. 이 모듈은 도메인 모듈에 정의된 서비스 인터페이스를 구현하고, 인프라스트럭처 모듈을 통해 데이터에 접근합니다.

## 주요 기능

- 도메인 서비스 인터페이스 구현
- 트랜잭션 관리
- 비즈니스 규칙 적용
- 도메인 이벤트 처리

## 모듈 구조

```
core/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── io/github/ruifoot/core/
│   │   │       ├── service/        # 서비스 구현체
│   │   │       └── util/           # 코어 모듈 유틸리티
│   │   └── resources/              # 리소스 파일
│   └── test/                       # 테스트 코드
└── build.gradle                    # 빌드 설정
```

## 의존성

이 모듈은 다음 모듈에 의존합니다:

- **domain**: 도메인 모델 및 서비스 인터페이스
- **infrastructure**: 데이터베이스, 보안, 외부 서비스 통합
- **common**: 공통 유틸리티, 응답 형식, 예외 처리 등

## 주요 의존성 라이브러리

- Spring Security Crypto: 비밀번호 암호화

## 서비스 구현 예시

Core 모듈은 도메인 모듈에 정의된 서비스 인터페이스를 구현합니다. 예를 들어:

```java
// 도메인 모듈의 서비스 인터페이스
public interface UserService {
    User findById(Long id);
    User register(UserRegistrationDto registrationDto);
    void updateProfile(Long userId, UserProfileDto profileDto);
}

// 코어 모듈의 서비스 구현체
@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
    
    @Override
    @Transactional
    public User register(UserRegistrationDto registrationDto) {
        // 비즈니스 로직 구현
        User user = new User();
        user.setUsername(registrationDto.getUsername());
        user.setEmail(registrationDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        user.setRole("USER");
        
        return userRepository.save(user);
    }
    
    @Override
    @Transactional
    public void updateProfile(Long userId, UserProfileDto profileDto) {
        // 비즈니스 로직 구현
        User user = findById(userId);
        user.setUsername(profileDto.getUsername());
        // 기타 필드 업데이트
        
        userRepository.save(user);
    }
}
```

## 테스트

Core 모듈은 단위 테스트와 통합 테스트를 포함합니다. 테스트는 다음 명령어로 실행할 수 있습니다:

```bash
./gradlew :core:test
```