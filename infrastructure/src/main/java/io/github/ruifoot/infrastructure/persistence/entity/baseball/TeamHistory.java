package io.github.ruifoot.infrastructure.persistence.entity.baseball;


import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 팀 연혁
 */
@Getter
@Setter
@Entity
@Table(name = "team_histories")
public class TeamHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private LocalDate date; // 연혁 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Teams teams;
}
