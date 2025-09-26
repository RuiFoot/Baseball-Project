package io.github.ruifoot.domain.repository;

import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeRepository {

    Page<NoticeResponseDto> findNotices(Pageable pageable);


}
