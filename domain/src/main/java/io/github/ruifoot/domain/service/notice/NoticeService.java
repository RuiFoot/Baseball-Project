package io.github.ruifoot.domain.service.notice;

import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

    Page<NoticeResponseDto> findNotices(Pageable pageable);
}
