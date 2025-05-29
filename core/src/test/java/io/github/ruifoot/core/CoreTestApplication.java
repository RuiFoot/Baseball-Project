package io.github.ruifoot.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("io.github.ruifoot.domain.model")
public class CoreTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(CoreTestApplication.class, args);
    }
}