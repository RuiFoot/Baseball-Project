package io.github.ruifoot.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "io.github.ruifoot.infrastructure.persistence.repository")
public class JpaConfig {
    // No additional configuration needed
}