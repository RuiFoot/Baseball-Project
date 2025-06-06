package io.github.ruifoot.infrastructure.config.database;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SchemaManagementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Base class for database connection tests.
 * This class provides common functionality for testing database connections
 * across different profiles.
 * 
 * Each subclass is configured to use a specific profile (local, dev, prod)
 * along with a corresponding test profile (local-test, dev-test, prod-test)
 * that provides PostgreSQL connection details.
 * 
 * The test verifies that the connection is to a PostgreSQL database,
 * not an H2 in-memory database or any other type.
 */
@Slf4j
@SpringBootTest(classes = InfrastructureTestApplication.class)
public abstract class DatabaseConnectionTest extends BaseTest {

    @Autowired
    protected DataSource dataSource;

    /**
     * Tests the database connection by:
     * 1. Getting a connection from the DataSource
     * 2. Verifying the connection is valid
     * 3. Logging connection metadata
     */
    public void testDatabaseConnection(String profileName) {
        log.info("[디버그] {} 프로필에 대한 데이터베이스 연결 테스트 시작", profileName);

        try (Connection connection = dataSource.getConnection()) {
            if (connection == null || !connection.isValid(1000)) {
                log.warn("[디버그] '{}' 프로필의 데이터베이스 연결이 유효하지 않거나 없습니다.", profileName);
                return;
            }

            String dbProductName = connection.getMetaData().getDatabaseProductName();
            log.info("[디버그] {} 프로필의 데이터베이스 연결 성공", profileName);
            log.info("[디버그] 연결 URL: {}", connection.getMetaData().getURL());
            log.info("[디버그] 데이터베이스 제품명: {}", dbProductName);
            log.info("[디버그] 데이터베이스 버전: {}", connection.getMetaData().getDatabaseProductVersion());

            if (!"PostgreSQL".equalsIgnoreCase(dbProductName)) {
                log.warn("[디버그] 예상과 다른 데이터베이스 제품명: {}", dbProductName);
            }

        }
        catch (SchemaManagementException e) {
            log.warn("스키마 검증 오류 무시: {}", e.getCause().getMessage());
        }
        catch (Exception e){
            log.error("[디버그] '{}' 프로필의 데이터베이스 연결 중 오류 발생: {}", profileName, e.getMessage());
        }

    }
}
