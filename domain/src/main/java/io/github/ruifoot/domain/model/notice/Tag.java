package io.github.ruifoot.domain.model.notice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Tag{
    private Long id;
    private String name;
    // private List<Notice> notices;
}
