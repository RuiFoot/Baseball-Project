package io.github.ruifoot.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("io.github.ruifoot.infrastructure.persistence.entity")
public class InfrastructureTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(InfrastructureTestApplication.class, args);
    }
}