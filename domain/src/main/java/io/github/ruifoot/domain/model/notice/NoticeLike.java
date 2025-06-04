package io.github.ruifoot.domain.model.notice;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import io.github.ruifoot.domain.model.user.Users;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeLike extends BaseTimeDomain {
    private Long id;
    private Notice notice;
    private Users user;
}
