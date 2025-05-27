package io.github.ruifoot.infrastructure.config;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {

    @GetMapping("/auth/login")
    public String login() {
        return "Login page";
    }

    @GetMapping("/swagger-ui/index.html")
    public String swaggerUi() {
        return "Swagger UI";
    }

    @GetMapping("/v3/api-docs")
    public String apiDocs() {
        return "API Docs";
    }

    @GetMapping("/api/users")
    public String getUsers() {
        return "Get Users API";
    }

    @PostMapping("/api/users")
    public String createUser(@RequestBody(required = false) String body) {
        return "Create User API";
    }

    @PutMapping("/api/users/{id}")
    public String updateUser(@PathVariable(name = "id") String id, @RequestBody(required = false) String body) {
        return "Update User API: " + id;
    }

    @DeleteMapping("/api/users/{id}")
    public String deleteUser(@PathVariable(name = "id") String id) {
        return "Delete User API: " + id;
    }
}
