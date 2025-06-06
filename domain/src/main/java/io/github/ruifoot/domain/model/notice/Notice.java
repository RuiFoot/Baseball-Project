package io.github.ruifoot.domain.model.notice;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import io.github.ruifoot.domain.model.user.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Notice extends BaseTimeDomain {
    private Long id;
    private String title;
    private String content;
    private int viewCount;
    private boolean pinned;
    private Users author;
    private Set<Tag> tags;
}
