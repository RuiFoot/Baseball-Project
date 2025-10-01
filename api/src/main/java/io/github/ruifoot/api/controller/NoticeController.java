package io.github.ruifoot.api.controller;

import io.github.ruifoot.common.dto.common.ResponseDto;
import io.github.ruifoot.common.dto.notice.request.NoticeRequestDto;
import io.github.ruifoot.common.dto.notice.response.NoticeDetailResponseDto;
import io.github.ruifoot.common.dto.notice.response.NoticeResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.util.ResponseUtil;
import io.github.ruifoot.domain.service.notice.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Notice", description = "공지사항 API")
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/notice") // API 버전 명시
public class NoticeController {
    private final NoticeService noticeService; // 서비스 이름도 통일하는 것이 좋습니다.

    /**
     * 공지사항 목록을 페이지별로 조회합니다.
     *
     * @param pageable 페이지 정보 (페이지 번호, 사이즈, 정렬)
     * @return 페이지에 해당하는 공지사항 목록
     */
    @Operation(summary = "공지사항 목록 조회", description = "공지사항 목록을 페이지별로 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @Parameters({
            @Parameter(name = "page", description = "페이지 번호 (0부터 시작)", in = ParameterIn.QUERY),
            @Parameter(name = "size", description = "페이지 크기", in = ParameterIn.QUERY),
            @Parameter(name = "sort", description = "정렬 (예: 'createdAt,desc')", in = ParameterIn.QUERY)
    })
    @GetMapping()
    public ResponseEntity<ResponseDto<?>> getNoticeList(
            @Parameter(hidden = true) @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            log.info("getNoticeList: pageable={}", pageable);
            Page<NoticeResponseDto> noticeList = noticeService.findNotices(pageable);
            return ResponseUtil.success(ResponseCode.SUCCESS, noticeList);
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST_BODY, e.getMessage());
        }
    }

    /**
     * 특정 공지사항의 상세 정보를 조회합니다.
     *
     * @param noticeId 조회할 공지사항의 ID
     * @return 공지사항 상세 정보
     */
    @Operation(summary = "공지사항 상세 조회", description = "특정 공지사항의 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음")
    })
    @GetMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<?>> getNoticeDetail(
            @Parameter(description = "공지사항 ID") @PathVariable Long noticeId) {
        try {
            NoticeDetailResponseDto noticeList = noticeService.findNoticeDetail(noticeId);
            return ResponseUtil.success(ResponseCode.SUCCESS, noticeList);
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
    }

    /**
     * 모든 공지사항 태그 목록을 조회합니다.
     *
     * @return 태그 목록
     */
    @Operation(summary = "공지사항 태그 목록 조회", description = "모든 공지사항 태그 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping("/tag")
    public ResponseEntity<ResponseDto<?>> getNoticeTagList() {
        try {
            return ResponseUtil.success(ResponseCode.SUCCESS, noticeService.getTagList());
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
    }

    /**
     * 새로운 공지사항을 작성합니다.
     *
     * @param user             인증된 사용자 정보
     * @param noticeRequestDto 공지사항 생성 요청 정보
     * @return 생성된 공지사항 정보
     */
    @Operation(summary = "공지사항 작성", description = "새로운 공지사항을 작성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "작성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping()
    public ResponseEntity<ResponseDto<?>> createNotice(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user,
            @RequestBody NoticeRequestDto noticeRequestDto) {
        try {
            log.info("createNotice: user={}, noticeRequestDto={}", user, noticeRequestDto);
            return ResponseUtil.success(ResponseCode.SUCCESS, noticeService.createNotice(noticeRequestDto, user));
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
    }

    /**
     * 기존 공지사항을 수정합니다.
     *
     * @param user             인증된 사용자 정보
     * @param noticeId         수정할 공지사항의 ID
     * @param noticeRequestDto 공지사항 수정 요청 정보
     * @return 수정된 공지사항 정보
     */
    @Operation(summary = "공지사항 수정", description = "기존 공지사항을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음")
    })
    @PutMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<?>> updateNotice(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user,
            @Parameter(description = "공지사항 ID") @PathVariable Long noticeId,
            @RequestBody NoticeRequestDto noticeRequestDto) {
        try {
            log.info("updateNotice: user={}, noticeId={}, noticeRequestDto={}", user, noticeId, noticeRequestDto);
            return ResponseUtil.success(ResponseCode.SUCCESS, noticeService.updateNotice(noticeId, noticeRequestDto, user));
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
    }

    /**
     * 공지사항을 삭제합니다.
     *
     * @param user     인증된 사용자 정보
     * @param noticeId 삭제할 공지사항의 ID
     * @return 응답 DTO
     */
    @Operation(summary = "공지사항 삭제", description = "공지사항을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "권한 없음"),
            @ApiResponse(responseCode = "404", description = "공지사항을 찾을 수 없음")
    })
    @DeleteMapping("/{noticeId}")
    public ResponseEntity<ResponseDto<?>> deleteNotice(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails user,
            @Parameter(description = "공지사항 ID") @PathVariable Long noticeId) {
        try {
            log.info("deleteNotice: user={}, noticeId={}", user, noticeId);
            noticeService.deleteNotice(noticeId, user);
            return ResponseUtil.success(ResponseCode.SUCCESS, null);
        } catch (Exception e) {
            return ResponseUtil.fail(ResponseCode.INVALID_REQUEST, e.getMessage());
        }
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
