package io.github.ruifoot.infrastructure.config;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.test.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
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
        // 인증 엔드포인트
        log.info("[DEBUG_LOG] 공개 엔드포인트 테스트 중: /auth/login");
        mockMvc.perform(get("/auth/login"))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] 응답 내용: {}", result.getResponse().getContentAsString());
                });

        // Swagger 엔드포인트
        log.info("[DEBUG_LOG] 공개 엔드포인트 테스트 중: /swagger-ui/index.html");
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk())
                .andDo(result -> log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus()));

        log.info("[DEBUG_LOG] 공개 엔드포인트 테스트 중: /v3/api-docs");
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andDo(result -> log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus()));
    }

    @Test
    public void testProtectedEndpointsRequireAuthentication() throws Exception {
        // 인증 없는 보호된 GET 엔드포인트
        log.info("[DEBUG_LOG] 인증 없이 보호된 GET 엔드포인트 테스트 중: /api/users");
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] 응답 내용: {}", result.getResponse().getContentAsString());
                });

        // 인증이 있는 보호된 GET 엔드포인트
        log.info("[DEBUG_LOG] 인증이 있는 보호된 GET 엔드포인트 테스트 중: /api/users");
        log.info("[DEBUG_LOG] 인증 사용자: user");
        mockMvc.perform(get("/api/users")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestConfig.getTestToken()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] 응답 내용: {}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testProtectedPostEndpointRequiresAuthentication() throws Exception {
        // 인증 없는 보호된 POST 엔드포인트
        log.info("[DEBUG_LOG] 인증 없이 보호된 POST 엔드포인트 테스트 중: /api/users");
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("{\"name\":\"Test User\"}"))
                .andExpect(status().isForbidden())
                .andDo(result -> log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus()));

        // 인증이 있는 보호된 POST 엔드포인트
        log.info("[DEBUG_LOG] 인증이 있는 보호된 POST 엔드포인트 테스트 중: /api/users");
        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content("{\"name\":\"Test User\"}")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestConfig.getTestToken()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] 응답 내용: {}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testProtectedPutEndpointRequiresAuthentication() throws Exception {
        // 인증 없는 보호된 PUT 엔드포인트
        log.info("[DEBUG_LOG] 인증 없이 보호된 PUT 엔드포인트 테스트 중: /api/users/1");
        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated User\"}"))
                .andExpect(status().isForbidden())
                .andDo(result -> log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus()));

        // 인증이 있는 보호된 PUT 엔드포인트
        log.info("[DEBUG_LOG] 인증이 있는 보호된 PUT 엔드포인트 테스트 중: /api/users/1");
        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content("{\"name\":\"Updated User\"}")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestConfig.getTestToken()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] 응답 내용: {}", result.getResponse().getContentAsString());
                });
    }

    @Test
    public void testProtectedDeleteEndpointRequiresAuthentication() throws Exception {
        // 인증 없는 보호된 DELETE 엔드포인트
        log.info("[DEBUG_LOG] 인증 없이 보호된 DELETE 엔드포인트 테스트 중: /api/users/1");
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isForbidden())
                .andDo(result -> log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus()));

        // 인증이 있는 보호된 DELETE 엔드포인트
        log.info("[DEBUG_LOG] 인증이 있는 보호된 DELETE 엔드포인트 테스트 중: /api/users/1");
        mockMvc.perform(delete("/api/users/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + TestConfig.getTestToken()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    log.info("[DEBUG_LOG] 응답 상태: {}", result.getResponse().getStatus());
                    log.info("[DEBUG_LOG] 응답 내용: {}", result.getResponse().getContentAsString());
                });
    }
}
