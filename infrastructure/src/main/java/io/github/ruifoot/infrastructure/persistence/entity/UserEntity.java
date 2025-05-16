package io.github.ruifoot.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private Long id;
    private String name;

}
