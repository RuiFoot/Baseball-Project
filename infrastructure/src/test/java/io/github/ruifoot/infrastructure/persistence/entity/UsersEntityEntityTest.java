package io.github.ruifoot.infrastructure.persistence.entity;

import io.github.ruifoot.infrastructure.InfrastructureTestApplication;
import io.github.ruifoot.infrastructure.persistence.entity.user.UsersEntity;
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
public class UsersEntityEntityTest extends BaseTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    public void testUserEntityCreation() {
        // Create a new user entity
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername("testuser");
        usersEntity.setEmail("testuser@example.com");
        usersEntity.setPasswordHash("hashedpassword");
        usersEntity.setRole("USER");
        usersEntity.setAdminApproved(false);

        // Save the user entity
        UsersEntity savedUsersEntity = userJpaRepository.save(usersEntity);

        // Verify that the user was saved successfully
        assertThat(savedUsersEntity).isNotNull();
        assertThat(savedUsersEntity.getId()).isNotNull();
        assertThat(savedUsersEntity.getUsername()).isEqualTo("testuser");
        assertThat(savedUsersEntity.getEmail()).isEqualTo("testuser@example.com");

        // Verify that createdAt and updatedAt fields are not null
        assertThat(savedUsersEntity.getCreatedAt()).isNotNull();
        assertThat(savedUsersEntity.getUpdatedAt()).isNotNull();

        System.out.println("[DEBUG_LOG] User created with ID: " + savedUsersEntity.getId());
        System.out.println("[DEBUG_LOG] CreatedAt: " + savedUsersEntity.getCreatedAt());
        System.out.println("[DEBUG_LOG] UpdatedAt: " + savedUsersEntity.getUpdatedAt());
    }
}