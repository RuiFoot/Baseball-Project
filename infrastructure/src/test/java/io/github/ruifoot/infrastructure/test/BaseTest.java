package io.github.ruifoot.infrastructure.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import lombok.extern.slf4j.Slf4j;

/**
 * 일관된 로깅을 위해 모든 테스트 클래스가 확장해야 하는 기본 테스트 클래스.
 * 이 클래스는 테스트 실행 세부 정보를 기록하기 위해 TestLoggerExtension을 적용합니다.
 * 또한 테스트 실패 시 한글 오류 메시지를 제공하기 위해 KoreanAssertionMessageExtension을 적용합니다.
 * 로거 인스턴스를 제공하기 위해 Lombok의 @Slf4j 어노테이션을 사용합니다.
 */
@Slf4j
@Extensions({
    @ExtendWith(TestLoggerExtension.class),
    @ExtendWith(KoreanAssertionMessageExtension.class)
})
public abstract class BaseTest {
    // 구현이 필요 없음 - 어노테이션이 모든 작업을 수행
}
