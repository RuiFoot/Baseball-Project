package io.github.ruifoot.infrastructure.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 테스트 실행 세부 정보를 기록하는 JUnit 5 확장.
 * 이 확장은 다음을 기록합니다:
 * - 테스트가 시작될 때, 테스트 이름 및 매개변수 포함
 * - 테스트가 완료될 때, 테스트 이름 및 실행 시간 포함
 */
@Slf4j
public class TestLoggerExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = testMethod.getName();

        // 메소드 이름으로 테스트 시작 로그
        log.info("[DEBUG_LOG] 테스트 시작: {}.{}", className, methodName);

        // 가능한 경우 테스트 매개변수 로그
        Parameter[] parameters = testMethod.getParameters();
        if (parameters.length > 0) {
            String paramInfo = Arrays.stream(parameters)
                    .map(p -> p.getType().getSimpleName() + " " + p.getName())
                    .collect(Collectors.joining(", "));
            log.info("[DEBUG_LOG] 테스트 매개변수: {}", paramInfo);
        }

        // 지속 시간 계산을 위한 시작 시간 저장
        context.getStore(ExtensionContext.Namespace.create(getClass(), testMethod))
                .put("start-time", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = testMethod.getName();

        // 테스트 지속 시간 계산
        long startTime = context.getStore(ExtensionContext.Namespace.create(getClass(), testMethod))
                .get("start-time", long.class);
        long duration = System.currentTimeMillis() - startTime;

        // 테스트 완료 상태 로그
        String status = context.getExecutionException().isPresent() ? "실패" : "성공";
        log.info("[DEBUG_LOG] 테스트 완료: {}.{} - {}", className, methodName, status);
        log.info("[DEBUG_LOG] 테스트 지속 시간: {}ms", duration);

        // 테스트 실패 시 예외 로그
        context.getExecutionException().ifPresent(ex -> 
            log.error("[DEBUG_LOG] 테스트가 예외로 실패: {}", ex.getMessage())
        );
    }
}
