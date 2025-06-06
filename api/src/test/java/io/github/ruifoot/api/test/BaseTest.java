package io.github.ruifoot.api.test;

import org.junit.jupiter.api.extension.ExtendWith;
import lombok.extern.slf4j.Slf4j;

/**
 * Base test class that all test classes should extend to get consistent logging.
 * This class applies the TestLoggerExtension to log test execution details.
 * It also uses Lombok's @Slf4j annotation to provide a logger instance.
 */
@Slf4j
@ExtendWith(TestLoggerExtension.class)
public abstract class BaseTest {
    // No implementation needed - the annotations do all the work
}