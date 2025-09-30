package io.github.ruifoot.common.dto.notice.request;

import jakarta.validation.constraints.NotBlank;

public record NoticeRequestDto(

        @NotBlank
        String title,
        String preview,

        @NotBlank
        String content,

        Long tagId
) {
}
