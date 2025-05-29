package io.github.ruifoot.infrastructure.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.opentest4j.AssertionFailedError;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 테스트 실패 시 영어 오류 메시지를 한글로 변환하는 JUnit 5 확장.
 * 이 확장은 AssertionFailedError를 가로채고 오류 메시지를 한글로 번역합니다.
 */
@Slf4j
public class KoreanAssertionMessageExtension implements TestExecutionExceptionHandler {

    // 영어 오류 메시지와 한글 번역의 매핑
    private static final Map<Pattern, String> ERROR_TRANSLATIONS = new HashMap<>();

    static {
        // AssertJ 오류 메시지 패턴
        ERROR_TRANSLATIONS.put(
                Pattern.compile("expected:<(.+)> but was:<(.+)>"),
                "기대값: <%s>, 실제값: <%s>"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting actual not to be null"),
                "실제값이 null이 아니어야 합니다"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting value to be true but was false"),
                "값이 true여야 하지만 false였습니다"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting value to be false but was true"),
                "값이 false여야 하지만 true였습니다"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting actual:\\s+<(.+)>\\s+to be null"),
                "실제값: <%s>이(가) null이어야 합니다"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting actual:\\s+<(.+)>\\s+not to be null"),
                "실제값: <%s>이(가) null이 아니어야 합니다"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting:\\s+<\"(.+)\">\\s+to be equal to:\\s+<\"(.+)\">\\s+but was not"),
                "기대값: \"%s\"이(가) \"%s\"와(과) 같아야 하지만 다릅니다"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Expecting:\\s+<(.+)>\\s+to be equal to:\\s+<(.+)>\\s+but was not"),
                "기대값: <%s>이(가) <%s>와(과) 같아야 하지만 다릅니다"
        );

        // Spring Test 오류 메시지 패턴
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Status expected:<(\\d+)> but was:<(\\d+)>"),
                "기대한 상태 코드: <%s>, 실제 상태 코드: <%s>"
        );
        ERROR_TRANSLATIONS.put(
                Pattern.compile("Response status expected:<(.+)> but was:<(.+)>"),
                "기대한 응답 상태: <%s>, 실제 응답 상태: <%s>"
        );
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        if (throwable instanceof AssertionFailedError) {
            AssertionFailedError error = (AssertionFailedError) throwable;
            String originalMessage = error.getMessage();

            // 기능 비활성화: 번역 대신 원본 메시지만 로깅
            log.error("[DEBUG_LOG] 원본 오류 메시지: {}", originalMessage);
            log.error("[DEBUG_LOG] 한글 번역 기능이 비활성화되었습니다.");

            // 원본 예외를 그대로 전달 (번역하지 않음)
        }

        // 모든 예외를 그대로 전달
        throw throwable;
    }

    private String translateErrorMessage(String originalMessage) {
        if (originalMessage == null) {
            return "알 수 없는 오류";
        }

        // 패턴 매칭을 통한 번역
        for (Map.Entry<Pattern, String> entry : ERROR_TRANSLATIONS.entrySet()) {
            Matcher matcher = entry.getKey().matcher(originalMessage);
            if (matcher.find()) {
                // 그룹 개수에 따라 포맷 문자열 적용
                if (matcher.groupCount() == 0) {
                    return entry.getValue();
                } else if (matcher.groupCount() == 1) {
                    return String.format(entry.getValue(), matcher.group(1));
                } else if (matcher.groupCount() == 2) {
                    return String.format(entry.getValue(), matcher.group(1), matcher.group(2));
                }
            }
        }

        // 매칭되는 패턴이 없으면 기본 메시지 반환
        return "테스트 실패: " + originalMessage;
    }
}
