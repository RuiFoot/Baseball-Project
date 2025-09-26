package io.github.ruifoot.common.dto.notice.response;

import java.time.OffsetDateTime;

public record NoticeResponseDto(
        Long id,
        String title,
        String content,
        int viewCount,
        Boolean pinned,
        String authorName,
        String tagName,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
