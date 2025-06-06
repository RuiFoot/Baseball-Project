package io.github.ruifoot.infrastructure.config.database;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.sql.SQLException;

/**
 * Test class for verifying database connections in the local environment.
 * 
 * This test uses both the "local" and "local-test" profiles:
 * - "local" profile activates the standard local environment configuration
 * - "local-test" profile provides specific PostgreSQL connection details for testing
 * 
 * The test verifies that the application can connect to a PostgreSQL database
 * in the local environment, not an H2 in-memory database.
 */
@Disabled
@ActiveProfiles({"local", "local-test"})
public class LocalDatabaseConnectionTest extends DatabaseConnectionTest {

    @BeforeAll
    static void loadEnv() {
        Dotenv dotenv = Dotenv.load(); // .env 파일 읽기
        // DB
        System.setProperty("DB_HOST", dotenv.get("DB_HOST"));
        System.setProperty("DB_PORT", dotenv.get("DB_PORT"));
        System.setProperty("DB_NAME", dotenv.get("DB_NAME"));
        System.setProperty("DB_USER", dotenv.get("DB_USER"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        // REDIS
        System.setProperty("REDIS_PASSWORD", dotenv.get("REDIS_PASSWORD"));
        System.setProperty("REDIS_PORT", dotenv.get("REDIS_PORT"));
    }


    @Test
    void testLocalDatabaseConnection() throws SQLException {
        testDatabaseConnection("local");
    }
}
