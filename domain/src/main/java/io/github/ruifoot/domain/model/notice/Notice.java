package io.github.ruifoot.domain.model.notice;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import io.github.ruifoot.domain.model.user.Users;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class Notice extends BaseTimeDomain {
    private Long id;
    private String title;
    private String preview;
    private String content;
    private int viewCount;
    private boolean pinned;
    private Users author;
    private Tag tag;
}
