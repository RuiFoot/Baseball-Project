package io.github.ruifoot.core.service.notice;


import io.github.ruifoot.domain.repository.NoticeRepository;
import io.github.ruifoot.domain.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
    private final NoticeRepository noticeRepository;
}
