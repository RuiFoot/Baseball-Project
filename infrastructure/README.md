# 인프라스트럭처 모듈

이 모듈은 야구 프로젝트의 인프라스트럭처 컴포넌트를 포함하고 있으며, 다음과 같은 요소들이 있습니다:

- 영속성 계층 (엔티티, 레포지토리)
- 보안 설정
- JWT 인증
- Redis 캐싱

## 코드 커버리지를 포함한 테스트 실행

이 모듈은 코드 커버리지 분석을 위해 JaCoCo로 구성되어 있습니다. 다음 Gradle 명령어를 사용하여 커버리지 보고서와 함께 테스트를 실행할 수 있습니다:

```bash
./gradlew :infrastructure:testWithCoverage
```

이 명령어는 다음을 수행합니다:
1. 인프라스트럭처 모듈의 모든 테스트 실행
2. 코드 커버리지 보고서 생성
3. 코드 커버리지가 최소 임계값(현재 50%)을 충족하는지 확인
4. 콘솔에 커버리지 요약 정보 출력

## 콘솔에서 커버리지 확인

테스트 실행 후 콘솔에서 직접 커버리지 정보를 확인할 수 있습니다. 다음과 같은 형식으로 출력됩니다:

```
========== JaCoCo Coverage Report ==========

Instructions: 1234/2345 (52.62%)
Branches:     123/234 (52.56%)
Lines:        345/456 (75.66%)
Methods:      56/78 (71.79%)
Classes:      12/15 (80.00%)

Detailed HTML report: file:///path/to/report/index.html

============================================
```

커버리지 정보만 확인하려면 다음 명령어를 사용할 수 있습니다:

```bash
./gradlew :infrastructure:printCoverageReport
```

이 명령어는 테스트를 실행하지 않고 기존에 생성된 커버리지 보고서를 기반으로 콘솔에 요약 정보를 출력합니다.

## 단일 테스트 클래스의 커버리지 확인

특정 테스트 클래스만 실행하고 해당 테스트의 커버리지를 확인하려면 다음 명령어를 사용할 수 있습니다:

```bash
./gradlew :infrastructure:testClassWithCoverage -PtestClass=io.github.ruifoot.infrastructure.config.SecurityConfigTest
```

이 명령어는 지정된 테스트 클래스만 실행하고, 해당 테스트로 인한 코드 커버리지를 콘솔에 출력합니다. 이는 특정 테스트가 어떤 코드를 커버하는지 빠르게 확인하는 데 유용합니다.

## 상세 커버리지 정보

커버리지 보고서는 이제 다음과 같은 상세 정보를 포함합니다:

1. 전체 프로젝트 커버리지 요약
2. 패키지별 커버리지 정보
3. 클래스별 라인 커버리지 정보

이를 통해 어떤 패키지나 클래스의 커버리지가 낮은지 쉽게 식별할 수 있습니다.

## 커버리지 보고서 보기

커버리지를 포함한 테스트를 실행한 후, 다음 형식으로 보고서가 생성됩니다:

- HTML: `infrastructure/build/jacocoHtml/index.html` (대화형 보기를 위해 브라우저에서 열기)
- XML: `infrastructure/build/reports/jacoco/test/jacocoTestReport.xml` (CI/CD 통합용)
- CSV: `infrastructure/build/reports/jacoco/test/jacocoTestReport.csv` (데이터 처리용)

HTML 보고서는 코드 커버리지를 탐색하기 위한 사용자 친화적인 인터페이스를 제공하며, 다음을 보여줍니다:
- 전체 프로젝트 커버리지
- 패키지 수준 커버리지
- 클래스 수준 커버리지
- 메소드 수준 커버리지
- 라인별 커버리지 하이라이팅

## 커버리지 측정 항목

JaCoCo는 다음과 같은 여러 커버리지 측정 항목을 보고합니다:

- **명령어(Instructions)**: Java 바이트코드 명령어 커버리지
- **분기(Branches)**: 제어 구조(if/else, switch)의 분기 커버리지
- **순환 복잡도(Cyclomatic Complexity)**: 코드 복잡성 커버리지
- **라인(Lines)**: 소스 코드 라인 커버리지
- **메소드(Methods)**: 메소드 커버리지
- **클래스(Classes)**: 클래스 커버리지

## 최소 커버리지 임계값

현재 최소 커버리지 임계값은 50%로 설정되어 있습니다. 이는 `build.gradle` 파일에서 `jacocoTestCoverageVerification` 태스크를 수정하여 조정할 수 있습니다.

## CI/CD 통합

XML 보고서는 Jenkins, GitHub Actions 또는 GitLab CI와 같은 CI/CD 시스템과 통합하는 데 사용할 수 있습니다. 대부분의 CI 시스템은 JaCoCo 보고서를 위한 플러그인이나 내장 지원을 제공합니다.

GitHub Actions 구성 예시:

```yaml
- name: Run tests with coverage
  run: ./gradlew :infrastructure:testWithCoverage

- name: Upload coverage report
  uses: actions/upload-artifact@v2
  with:
    name: jacoco-report
    path: infrastructure/build/jacocoHtml/
```
