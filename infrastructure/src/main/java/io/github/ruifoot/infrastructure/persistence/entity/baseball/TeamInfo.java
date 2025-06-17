package io.github.ruifoot.infrastructure.persistence.entity.baseball;

import io.github.ruifoot.infrastructure.persistence.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "team_info")
public class TeamInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    // 추가 필드
    @Column(name = "region")
    private String region;
    @Column(name = "league")
    private String league;
    @Column(name = "training_place")
    private String trainingPlace;
    @Column(name = "manager")
    private String manager;
    @Column(name = "notice")
    private String notice;
    @Column(name = "training_schedule")
    private String trainingSchedule;
    @Column(name = "average_age")
    private Double averageAge;
    @Column(name = "member_count")
    private Integer memberCount;
    @Column(name = "monthly_fee")
    private Integer monthlyFee;

    @OneToOne
    @JoinColumn(name = "team_id")
    private Teams teams;

}
