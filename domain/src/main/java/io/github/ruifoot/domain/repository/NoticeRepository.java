package io.github.ruifoot.domain.repository;

import io.github.ruifoot.common.dto.notice.request.NoticeRequestDto;
import io.github.ruifoot.common.dto.notice.response.NoticeDetailResponseDto;
import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.domain.model.notice.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface NoticeRepository {

    Page<NoticeResponseDto> findNotices(Pageable pageable);


    List<Tag> findTags();

    Tag findTagById(long id);

    NoticeDetailResponseDto findNoticeDetail(Long noticeId);

    NoticeDetailResponseDto save(NoticeRequestDto noticeRequestDto, UserDetails user);

    NoticeRequestDto update(Long noticeId, NoticeRequestDto noticeRequestDto, UserDetails user);

    void delete(Long noticeId, UserDetails user);

}
