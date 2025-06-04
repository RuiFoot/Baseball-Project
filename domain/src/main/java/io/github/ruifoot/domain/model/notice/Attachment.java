package io.github.ruifoot.domain.model.notice;

import io.github.ruifoot.domain.model.BaseTimeDomain;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Attachment extends BaseTimeDomain {
    private Long id;
    private String fileName;
    private String fileUrl;
    private Notice notice;
}
