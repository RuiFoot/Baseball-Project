# 한글 단언(Assertion) 메시지 기능 (실험적 기능 / 현재 비활성화)

이 디렉토리에 포함된 코드는 JUnit 5 테스트 실패 시, 표준 영어 오류 메시지를 한글로 변환하여 보여주는 실험적인 기능입니다.

**⚠️ 주의: 이 기능은 현재 기본적으로 비활성화되어 있으며, 프로덕션 코드에 영향을 주지 않습니다.**

## 📜 개요

테스트 주도 개발(TDD) 과정에서 발생하는 단언 실패 메시지는 개발자에게 중요한 피드백을 제공합니다. 이 기능은 AssertJ, JUnit 등에서 생성하는 표준 실패 메시지(예: `expected: <X> but was: <Y>`)를 더 직관적인 한글로 변환하여 개발자의 빠른 이해를 돕기 위해 만들어졌습니다.

## ✨ 주요 컴포넌트

- **`KoreanAssertionMessageExtension`**: JUnit 5의 `TestExecutionExceptionHandler`를 구현한 확장 클래스입니다. 테스트에서 `AssertionFailedError`가 발생했을 때, 오류 메시지를 가로채서 정해진 패턴에 따라 한글로 변환하고 로깅합니다.
- **`BaseTest`**: `@ExtendWith(KoreanAssertionMessageExtension.class)`를 사용하여 테스트 클래스에 한글 메시지 기능을 적용하기 위한 기반 클래스입니다. (현재는 주석 처리 등으로 비활성화)
- **`KoreanAssertionMessageTest`**: 이 기능이 올바르게 동작하는지 검증하기 위한 샘플 테스트 클래스입니다. 의도적으로 실패하는 테스트 케이스를 포함하고 있습니다.

## 💡 활성화 및 테스트 방법 (참고용)

이 기능을 로컬에서 테스트하려면 다음 단계를 따를 수 있습니다.

1.  `BaseTest` 클래스나 개별 테스트 클래스에 `@ExtendWith(KoreanAssertionMessageExtension.class)` 어노테이션을 추가합니다.
2.  `KoreanAssertionMessageExtension` 내부의 로직이 활성화되어 있는지 확인합니다.
3.  `KoreanAssertionMessageTest` 또는 직접 작성한 실패 테스트를 실행합니다.

```bash
# 특정 테스트 실행
./gradlew :infrastructure:test --tests "*.KoreanAssertionMessageTest"
```

실행 결과로 콘솔에 영어 오류 메시지와 함께 변환된 한글 오류 메시지가 출력되는 것을 확인할 수 있습니다.

## ⛔ 비활성화 상태

현재 이 기능은 CI/CD 빌드 과정이나 다른 개발자에게 영향을 주지 않도록 `BaseTest` 등에서 기능이 활성화되어 있지 않습니다. 아이디어와 구현을 기록으로 남겨두기 위해 삭제하지 않고 유지하고 있습니다.