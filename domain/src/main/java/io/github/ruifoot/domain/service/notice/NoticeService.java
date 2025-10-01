package io.github.ruifoot.domain.service.notice;

import io.github.ruifoot.common.dto.notice.request.NoticeRequestDto;
import io.github.ruifoot.common.dto.notice.response.NoticeDetailResponseDto;
import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.domain.model.notice.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface NoticeService {

    Page<NoticeResponseDto> findNotices(Pageable pageable);

    List<Tag> getTagList();

    NoticeDetailResponseDto findNoticeDetail(Long noticeId);

    NoticeDetailResponseDto createNotice(NoticeRequestDto noticeRequestDto, UserDetails user);

    NoticeRequestDto updateNotice(Long noticeId, NoticeRequestDto noticeRequestDto, UserDetails user);

    void deleteNotice(Long noticeId, UserDetails user);
}
