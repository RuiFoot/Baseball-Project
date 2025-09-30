package io.github.ruifoot.common.dto.notice.response;

import java.time.OffsetDateTime;

public record NoticeDetailResponseDto(

        Long id,

        String title,
        String preview,
        String content,
        int viewCount,
        boolean isPinned,
        String author,
        String tag,


        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
){

}