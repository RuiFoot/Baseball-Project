package io.github.ruifoot.infrastructure.persistence.entity.baseball;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * 팀의 기본 정보
 */
@Getter
@Setter
@Entity
@Table(name = "teams")
public class Teams extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "founded_date")
    private LocalDate foundedDate;

    @OneToOne(mappedBy = "teams", cascade = CascadeType.ALL)
    private TeamContact contact;

    @OneToOne(mappedBy = "teams", cascade = CascadeType.ALL)
    private TeamInfo teamInfo;


}