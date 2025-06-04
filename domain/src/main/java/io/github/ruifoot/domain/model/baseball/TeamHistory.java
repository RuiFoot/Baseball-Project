package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 팀 연혁
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TeamHistory extends BaseTimeDomain {
    private Long id;
    private String description;
    private LocalDate date; // 연혁 날짜
    private Teams teams;
}