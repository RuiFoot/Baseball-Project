package io.github.ruifoot.infrastructure.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 한글 오류 메시지 확장을 테스트하기 위한 테스트 클래스.
 * 이 클래스는 의도적으로 실패하는 테스트를 포함하여 한글 오류 메시지가 올바르게 표시되는지 확인합니다.
 * 참고: 이 테스트는 실제 테스트 실행에서는 제외되어야 합니다.
 */
@Slf4j
@Disabled("한글 오류 메시지 테스트는 의도적으로 실패하므로 일반 테스트 실행에서 제외됩니다")
@ExtendWith(MockitoExtension.class)
public class KoreanAssertionMessageTest extends BaseTest {

    @Test
    public void testAssertEqualsWithKoreanMessage() {
        log.info("[DEBUG_LOG] 한글 오류 메시지 테스트 시작");

        // 이 테스트는 의도적으로 실패합니다
        String expected = "기대값";
        String actual = "실제값";

        log.info("[DEBUG_LOG] 기대값: {}, 실제값: {}", expected, actual);

        // 이 assertion은 실패하고 한글 오류 메시지를 표시해야 합니다
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testAssertNullWithKoreanMessage() {
        log.info("[DEBUG_LOG] null 검증 한글 오류 메시지 테스트 시작");

        // 이 테스트는 의도적으로 실패합니다
        String notNull = "null이 아닌 값";

        log.info("[DEBUG_LOG] null이어야 하는 값: {}", notNull);

        // 이 assertion은 실패하고 한글 오류 메시지를 표시해야 합니다
        assertThat(notNull).isNull();
    }

    @Test
    public void testAssertTrueWithKoreanMessage() {
        log.info("[DEBUG_LOG] boolean 검증 한글 오류 메시지 테스트 시작");

        // 이 테스트는 의도적으로 실패합니다
        boolean falseValue = false;

        log.info("[DEBUG_LOG] true여야 하는 값: {}", falseValue);

        // 이 assertion은 실패하고 한글 오류 메시지를 표시해야 합니다
        assertThat(falseValue).isTrue();
    }
}
