package io.github.ruifoot.common.dto.notice.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

@Schema(description = "공지사항 상세 응답 DTO")
@Builder
public record NoticeDetailResponseDto(

        @Schema(description = "공지사항 ID")
        Long id,

        @Schema(description = "제목")
        String title,
        @Schema(description = "미리보기")
        String preview,
        @Schema(description = "내용")
        String content,
        @Schema(description = "조회수")
        int viewCount,
        @Schema(description = "고정 여부")
        boolean isPinned,
        @Schema(description = "작성자 이름")
        String author,
        @Schema(description = "태그 이름")
        String tag,

        @Schema(description = "생성일")
        OffsetDateTime createdAt,
        @Schema(description = "수정일")
        OffsetDateTime updatedAt
){
}