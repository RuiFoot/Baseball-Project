package io.github.ruifoot.core.service.notice;


import io.github.ruifoot.common.dto.notice.request.NoticeRequestDto;
import io.github.ruifoot.common.dto.notice.response.NoticeDetailResponseDto;
import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.domain.model.notice.Tag;
import io.github.ruifoot.domain.repository.NoticeRepository;
import io.github.ruifoot.domain.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;


    @Override
    public Page<NoticeResponseDto> findNotices(Pageable pageable) {

        return noticeRepository.findNotices(pageable);
    }

    @Override
    public List<Tag> getTagList() {
        return noticeRepository.findTags();
    }

    @Override
    @Transactional
    public NoticeDetailResponseDto findNoticeDetail(Long noticeId) {
        return noticeRepository.findNoticeDetail(noticeId);
    }

    @Override
    @Transactional
    public NoticeDetailResponseDto createNotice(NoticeRequestDto noticeRequestDto, UserDetails user) {

        return noticeRepository.save(noticeRequestDto, user);
    }

    @Override
    @Transactional
    public NoticeRequestDto updateNotice(Long noticeId, NoticeRequestDto noticeRequestDto, UserDetails user) {
        return noticeRepository.update(noticeId, noticeRequestDto, user);
    }

    @Override
    @Transactional
    public void deleteNotice(Long noticeId, UserDetails user) {
        noticeRepository.delete(noticeId, user);
    }
}
