# 한글 테스트 오류 메시지 기능 (현재 비활성화됨)

이 기능은 테스트 실패 시 오류 메시지를 한글로 표시하는 기능을 제공합니다. **현재 이 기능은 비활성화되어 있습니다.**

## 구현 내용

1. `KoreanAssertionMessageExtension` - JUnit 5 확장 클래스
   - 테스트 실패 시 발생하는 `AssertionFailedError`를 가로채는 코드가 포함되어 있습니다.
   - 다양한 assertion 패턴에 대한 한글 번역 패턴이 정의되어 있습니다.
   - 현재는 원본 오류 메시지만 로깅하고 번역 기능은 비활성화되어 있습니다.

2. `BaseTest` 클래스 업데이트
   - `KoreanAssertionMessageExtension`이 포함되어 있지만 현재는 실제 번역 기능이 작동하지 않습니다.

3. `KoreanAssertionMessageTest` - 테스트 클래스
   - 한글 오류 메시지 기능을 검증하기 위한 테스트 클래스입니다.
   - 현재는 기능이 비활성화되어 있어 영어 오류 메시지가 표시됩니다.

## 지원하는 오류 메시지 패턴

다음과 같은 오류 메시지 패턴을 한글로 번역합니다:

### AssertJ 오류 메시지
- `expected:<X> but was:<Y>` → `기대값: <X>, 실제값: <Y>`
- `Expecting actual not to be null` → `실제값이 null이 아니어야 합니다`
- `Expecting value to be true but was false` → `값이 true여야 하지만 false였습니다`
- `Expecting value to be false but was true` → `값이 false여야 하지만 true였습니다`
- `Expecting actual: <X> to be null` → `실제값: <X>이(가) null이어야 합니다`
- `Expecting actual: <X> not to be null` → `실제값: <X>이(가) null이 아니어야 합니다`
- `Expecting: <"X"> to be equal to: <"Y"> but was not` → `기대값: "X"이(가) "Y"와(과) 같아야 하지만 다릅니다`
- `Expecting: <X> to be equal to: <Y> but was not` → `기대값: <X>이(가) <Y>와(과) 같아야 하지만 다릅니다`

### Spring Test 오류 메시지
- `Status expected:<X> but was:<Y>` → `기대한 상태 코드: <X>, 실제 상태 코드: <Y>`
- `Response status expected:<X> but was:<Y>` → `기대한 응답 상태: <X>, 실제 응답 상태: <Y>`

## 테스트 방법

`KoreanAssertionMessageTest` 클래스에는 의도적으로 실패하는 테스트가 포함되어 있습니다. 이 테스트를 실행하면 한글 오류 메시지가 표시되는 것을 확인할 수 있습니다.

```bash
# 특정 테스트 메소드 실행
./gradlew test --tests "io.github.ruifoot.infrastructure.test.KoreanAssertionMessageTest.testAssertEqualsWithKoreanMessage"

# 모든 테스트 메소드 실행
./gradlew test --tests "io.github.ruifoot.infrastructure.test.KoreanAssertionMessageTest"
```

## 주의사항

- `KoreanAssertionMessageTest` 클래스는 의도적으로 실패하는 테스트를 포함하고 있으므로, 일반적인 테스트 실행에서는 제외하는 것이 좋습니다.
- 새로운 assertion 패턴을 사용하는 경우, `KoreanAssertionMessageExtension` 클래스에 해당 패턴에 대한 한글 번역을 추가해야 합니다.
