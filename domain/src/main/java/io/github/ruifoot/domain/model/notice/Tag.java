package io.github.ruifoot.domain.model.notice;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tag extends BaseTimeDomain {
    private Long id;
    private String name;
    private List<Notice> notices;
}
