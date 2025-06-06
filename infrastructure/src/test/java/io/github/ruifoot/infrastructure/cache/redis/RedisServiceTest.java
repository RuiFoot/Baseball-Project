package io.github.ruifoot.infrastructure.cache.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import io.github.ruifoot.infrastructure.InfrastructureTestApplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = InfrastructureTestApplication.class)
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void contextLoads() {
        // This test will fail if the application context cannot be loaded
        // due to the bean conflict we're trying to resolve
        assertNotNull(redisService, "RedisService should be autowired successfully");
        System.out.println("[DEBUG_LOG] RedisService autowired successfully: " + (redisService != null));
    }
}