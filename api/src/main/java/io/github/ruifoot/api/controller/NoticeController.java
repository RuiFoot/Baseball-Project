package io.github.ruifoot.api.controller;


import io.github.ruifoot.domain.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공지사항 관련 컨트롤러
 */
@RestController(value = "notice")
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
}
