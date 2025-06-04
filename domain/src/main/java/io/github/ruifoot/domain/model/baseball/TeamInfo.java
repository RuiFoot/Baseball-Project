package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamInfo extends BaseTimeDomain {
    private Long id;
    private String region;
    private String league;
    private String trainingPlace;
    private String manager;
    private String notice;
    private String trainingSchedule;
    private Double averageAge;
    private Integer memberCount;
    private Integer monthlyFee;
    private Teams teams;
}