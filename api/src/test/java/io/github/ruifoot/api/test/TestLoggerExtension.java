package io.github.ruifoot.api.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * JUnit 5 extension that logs test execution details.
 * This extension logs:
 * - When a test starts, including the test name and parameters
 * - When a test completes, including the test name and execution time
 */
@Slf4j
public class TestLoggerExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = testMethod.getName();

        // Log test start with method name
        log.info("[DEBUG_LOG] Starting test: {}.{}", className, methodName);

        // Log test parameters if available
        Parameter[] parameters = testMethod.getParameters();
        if (parameters.length > 0) {
            String paramInfo = Arrays.stream(parameters)
                    .map(p -> p.getType().getSimpleName() + " " + p.getName())
                    .collect(Collectors.joining(", "));
            log.info("[DEBUG_LOG] Test parameters: {}", paramInfo);
        }

        // Store start time for duration calculation
        context.getStore(ExtensionContext.Namespace.create(getClass(), testMethod))
                .put("start-time", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        Method testMethod = context.getRequiredTestMethod();
        String className = context.getRequiredTestClass().getSimpleName();
        String methodName = testMethod.getName();

        // Calculate test duration
        long startTime = context.getStore(ExtensionContext.Namespace.create(getClass(), testMethod))
                .get("start-time", long.class);
        long duration = System.currentTimeMillis() - startTime;

        // Log test completion status
        String status = context.getExecutionException().isPresent() ? "FAILED" : "PASSED";
        log.info("[DEBUG_LOG] Test completed: {}.{} - {}", className, methodName, status);
        log.info("[DEBUG_LOG] Test duration: {}ms", duration);

        // Log exception if test failed
        context.getExecutionException().ifPresent(ex -> 
            log.error("[DEBUG_LOG] Test failed with exception: {}", ex.getMessage())
        );
    }
}