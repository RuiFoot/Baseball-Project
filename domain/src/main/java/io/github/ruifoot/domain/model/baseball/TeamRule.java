package io.github.ruifoot.domain.model.baseball;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TeamRule extends BaseTimeDomain {
    private Long id;
    private String content;
    private Teams teams;
}