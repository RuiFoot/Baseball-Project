package io.github.ruifoot.infrastructure.config;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = TestController.class)
@Import({SecurityConfig.class, TestConfig.class})
@ContextConfiguration(classes = InfrastructureTestApplication.class)
public class SecurityConfigTest extends BaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPublicEndpointsAreAccessible() throws Exception {
        // Auth endpoints
        log.info("[DEBUG_LOG] Testing public endpoint: /auth/login");
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] Response content: {}", result.getResponse().getContentAsString());
                });

        // Swagger endpoints
        log.info("[DEBUG_LOG] Testing public endpoint: /swagger-ui/index.html");
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                });

        log.info("[DEBUG_LOG] Testing public endpoint: /v3/api-docs");
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                });
    }

    @Test
    public void testProtectedEndpointsRequireAuthentication() throws Exception {
        // Protected GET endpoint without authentication
        log.info("[DEBUG_LOG] Testing protected GET endpoint without authentication: /api/users");
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isUnauthorized())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] Response content: {}", result.getResponse().getContentAsString());
                });

        // Protected GET endpoint with authentication
        log.info("[DEBUG_LOG] Testing protected GET endpoint with authentication: /api/users");
        log.info("[DEBUG_LOG] Authentication user: user");
        mockMvc.perform(get("/api/users")
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] Response content: {}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testProtectedPostEndpointRequiresAuthentication() throws Exception {
        // Protected POST endpoint without authentication
        log.info("[DEBUG_LOG] Testing protected POST endpoint without authentication: /api/users");
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("{\"name\":\"Test User\"}"))
                .andExpect(status().isUnauthorized())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                });

        // Protected POST endpoint with authentication
        log.info("[DEBUG_LOG] Testing protected POST endpoint with authentication: /api/users");
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("{\"name\":\"Test User\"}")
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] Response content: {}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testProtectedPutEndpointRequiresAuthentication() throws Exception {
        // Protected PUT endpoint without authentication
        log.info("[DEBUG_LOG] Testing protected PUT endpoint without authentication: /api/users/1");
        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated User\"}"))
                .andExpect(status().isUnauthorized())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                });

        // Protected PUT endpoint with authentication
        log.info("[DEBUG_LOG] Testing protected PUT endpoint with authentication: /api/users/1");
        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated User\"}")
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] Response content: {}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testProtectedDeleteEndpointRequiresAuthentication() throws Exception {
        // Protected DELETE endpoint without authentication
        log.info("[DEBUG_LOG] Testing protected DELETE endpoint without authentication: /api/users/1");
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isUnauthorized())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                });

        // Protected DELETE endpoint with authentication
        log.info("[DEBUG_LOG] Testing protected DELETE endpoint with authentication: /api/users/1");
        mockMvc.perform(delete("/api/users/1")
                .with(SecurityMockMvcRequestPostProcessors.user("user")))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] Response status: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] Response content: {}", result.getResponse().getContentAsString());
                });
    }
}
