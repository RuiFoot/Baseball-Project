package io.github.ruifoot.common.dto.notice.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "공지사항 목록 응답 DTO")
public record NoticeResponseDto(

        @Schema(description = "공지사항 ID")
        Long id,
        @Schema(description = "제목")
        String title,
        @Schema(description = "미리보기")
        String preview,
        @Schema(description = "조회수")
        int viewCount,
        @Schema(description = "고정 여부")
        Boolean pinned,
        @Schema(description = "작성자 이름")
        String authorName,
        @Schema(description = "태그 이름")
        String tagName,

        @Schema(description = "생성일")
        OffsetDateTime createdAt,
        @Schema(description = "수정일")
        OffsetDateTime updatedAt
) {
}
