package io.github.ruifoot.api.controller;

import io.github.ruifoot.domain.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 공지사항 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcements") // API 버전 명시
public class AnnouncementController {
    // private final AnnouncementService announcementService; // 서비스 이름도 통일하는 것이 좋습니다.

    /*
    TODO[AnnouncementController]: 공지사항 목록 조회 (GET /)
     - Query Params: ?page=0&size=10&sort=createdAt,desc&category=모임
     - 프론트엔드의 검색/필터링(카테고리, 정렬) 기능 지원 필요
     - Response: Paged<AnnouncementPreviewDto>
    */

    /*
    TODO[AnnouncementController]: 공지사항 상세 조회 (GET /{announcementId})
     - PathVariable: announcementId
     - 조회수 증가 로직 필요
     - Response: AnnouncementDetailDto (첨부파일, 댓글 목록 포함)
    */

    /*
    TODO[AnnouncementController]: 공지사항 작성 (POST /)
     - Admin 권한 필요
     - RequestBody: CreateAnnouncementRequest (title, content, category, pinned, attachments)
     - 알림 발송 옵션(sendNotification)에 따른 알림 생성 로직 호출 필요
     - Response: Created Announcement ID
    */

    /*
    TODO[AnnouncementController]: 공지사항 수정 (PUT /{announcementId})
     - Admin 권한 필요, 작성자 본인 확인 로직 추가 고려
     - PathVariable: announcementId
     - RequestBody: UpdateAnnouncementRequest (title, content, category, pinned, attachments)
    */

    /*
    TODO[AnnouncementController]: 공지사항 삭제 (DELETE /{announcementId})
     - Admin 권한 필요, 작성자 본인 확인 로직 추가 고려
     - PathVariable: announcementId
    */

    /*
    TODO[AnnouncementController]: 댓글 작성 (POST /{announcementId}/comments)
     - 로그인한 사용자
     - RequestBody: { "content": "댓글 내용" }
    */

    /*
    TODO[AnnouncementController]: 댓글 삭제 (DELETE /{announcementId}/comments/{commentId})
     - 댓글 작성자 본인 또는 Admin 권한
    */
}
