package io.github.ruifoot.infrastructure.persistence.entity;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.persistence.entity.user.User;
import io.github.ruifoot.infrastructure.persistence.repository.jpa.UserJpaRepository;
import io.github.ruifoot.infrastructure.test.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = InfrastructureTestApplication.class)
@ActiveProfiles("test")
public class UserEntityTest extends BaseTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    public void testUserEntityCreation() {
        // Create a new user entity
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPasswordHash("hashedpassword");
        user.setRole("USER");
        user.setAdminApproved(false);

        // Save the user entity
        User savedUser = userJpaRepository.save(user);

        // Verify that the user was saved successfully
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
        assertThat(savedUser.getEmail()).isEqualTo("testuser@example.com");

        // Verify that createdAt and updatedAt fields are not null
        assertThat(savedUser.getCreatedAt()).isNotNull();
        assertThat(savedUser.getUpdatedAt()).isNotNull();

        System.out.println("[DEBUG_LOG] User created with ID: " + savedUser.getId());
        System.out.println("[DEBUG_LOG] CreatedAt: " + savedUser.getCreatedAt());
        System.out.println("[DEBUG_LOG] UpdatedAt: " + savedUser.getUpdatedAt());
    }
}