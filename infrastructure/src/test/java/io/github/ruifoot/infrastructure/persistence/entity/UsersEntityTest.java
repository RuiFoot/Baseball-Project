package io.github.ruifoot.infrastructure.persistence.entity;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.persistence.entity.user.Users;
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
public class UsersEntityTest extends BaseTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    public void testUserEntityCreation() {
        // Create a new user entity
        Users users = new Users();
        users.setUsername("testuser");
        users.setEmail("testuser@example.com");
        users.setPasswordHash("hashedpassword");
        users.setRole("USER");
        users.setAdminApproved(false);

        // Save the user entity
        Users savedUsers = userJpaRepository.save(users);

        // Verify that the user was saved successfully
        assertThat(savedUsers).isNotNull();
        assertThat(savedUsers.getId()).isNotNull();
        assertThat(savedUsers.getUsername()).isEqualTo("testuser");
        assertThat(savedUsers.getEmail()).isEqualTo("testuser@example.com");

        // Verify that createdAt and updatedAt fields are not null
        assertThat(savedUsers.getCreatedAt()).isNotNull();
        assertThat(savedUsers.getUpdatedAt()).isNotNull();

        System.out.println("[DEBUG_LOG] User created with ID: " + savedUsers.getId());
        System.out.println("[DEBUG_LOG] CreatedAt: " + savedUsers.getCreatedAt());
        System.out.println("[DEBUG_LOG] UpdatedAt: " + savedUsers.getUpdatedAt());
    }
}