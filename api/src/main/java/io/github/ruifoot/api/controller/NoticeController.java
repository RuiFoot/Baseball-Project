package io.github.ruifoot.api.controller;

import io.github.ruifoot.common.dto.common.ResponseDto;
import io.github.ruifoot.common.dto.notice.request.NoticeRequestDto;
import io.github.ruifoot.common.dto.notice.response.NoticeDetailResponseDto;
import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ResponseUtil;
import io.github.ruifoot.domain.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * 공지사항 관련 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice") // API 버전 명시
public class NoticeController {
    private final NoticeService noticeService; // 서비스 이름도 통일하는 것이 좋습니다.
    /*
    TODO[AnnouncementController]: 공지사항 목록 조회 (GET /)
     - Query Params: ?page=0&size=10&sort=createdAt,desc&category=모임
     - 프론트엔드의 검색/필터링(카테고리, 정렬) 기능 지원 필요
     - Response: Paged<AnnouncementPreviewDto>
    */

    @GetMapping()
    public ResponseEntity<ResponseDto<?>> getNoticeList(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        try{
            Page<NoticeResponseDto> noticeList = noticeService.findNotices(pageable);

            return ResponseUtil.success(ResponseCode.SUCCESS, noticeList);
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST_BODY, e.getMessage());
        }

    }


    /*
    TODO[AnnouncementController]: 공지사항 상세 조회 (GET /{noticeId})
     - PathVariable: noticeId
     - 조회수 증가 로직 필요
     - Response: AnnouncementDetailDto (첨부파일, 댓글 목록 포함)
    */

    @GetMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<?>> getNoticeDetail(@PathVariable Long noticeId) {
        try{
            NoticeDetailResponseDto noticeList = noticeService.findNoticeDetail(noticeId);

            return ResponseUtil.success(ResponseCode.SUCCESS, noticeList);
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
    }


    @GetMapping("/tag")
    public ResponseEntity<ResponseDto<?>> getNoticeTagList() {
        try{
            return ResponseUtil.success(ResponseCode.SUCCESS,noticeService.getTagList());
        }
        catch (Exception e){
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST,e.getMessage());
        }
    }


    /*
    TODO[AnnouncementController]: 공지사항 작성 (POST /)
     - Admin 권한 필요
     - RequestBody: CreateAnnouncementRequest (title, content, category, pinned, attachments)
     - 알림 발송 옵션(sendNotification)에 따른 알림 생성 로직 호출 필요
     - Response: Created Announcement ID
    */

    @PostMapping()
    public ResponseEntity<ResponseDto<?>> createNotice(@AuthenticationPrincipal UserDetails user, @RequestBody NoticeRequestDto noticeRequestDto) {
        try {
            log.info("createNotice: user={}, noticeRequestDto={}", user, noticeRequestDto);
            return ResponseUtil.success(ResponseCode.SUCCESS, noticeService.createNotice(noticeRequestDto, user));
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
    }


    /*
    TODO[AnnouncementController]: 공지사항 수정 (PUT /{noticeId})
     - Admin 권한 필요, 작성자 본인 확인 로직 추가 고려
     - PathVariable: noticeId
     - RequestBody: UpdateAnnouncementRequest (title, content, category, pinned, attachments)
    */

    @PutMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<?>> updateNotice(@PathVariable Long noticeId) {
        return null;
    }


    /*
    TODO[AnnouncementController]: 공지사항 삭제 (DELETE /{noticeId})
     - Admin 권한 필요, 작성자 본인 확인 로직 추가 고려
     - PathVariable: noticeId
    */

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<?>> deleteNotice(@PathVariable Long noticeId) {
        return null;
    }

    /*
    TODO[AnnouncementController]: 댓글 작성 (POST /{noticeId}/comments)
     - 로그인한 사용자
     - RequestBody: { "content": "댓글 내용" }
    */

    /*
    TODO[AnnouncementController]: 댓글 삭제 (DELETE /{noticeId}/comments/{commentId})
     - 댓글 작성자 본인 또는 Admin 권한
    */
}
