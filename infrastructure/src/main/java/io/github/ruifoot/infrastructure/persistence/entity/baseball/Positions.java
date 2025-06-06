package io.github.ruifoot.infrastructure.persistence.entity.baseball;

import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCategory;
import io.github.ruifoot.infrastructure.persistence.entity.enums.PositionCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "positions")
public class Positions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code")
    private PositionCode code;

    @Size(max = 50)
    @NotNull
    @Column(name = "name_kr", nullable = false, length = 50)
    private String nameKr;

    @Size(max = 50)
    @Column(name = "name_en", length = 50)
    private String nameEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PositionCategory category;

}
