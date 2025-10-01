package io.github.ruifoot.common.dto.notice.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "공지사항 생성/수정 요청 DTO")
public record NoticeRequestDto(

        @Schema(description = "제목", example = "새로운 공지사항입니다.")
        @NotBlank
        String title,
        @Schema(description = "미리보기", example = "공지사항 내용 미리보기입니다.")
        String preview,

        @Schema(description = "내용", example = "공지사항의 전체 내용입니다.")
        @NotBlank
        String content,

        @Schema(description = "태그 ID", example = "1")
        Long tagId
) {
}
