package io.github.ruifoot.domain.model;

import lombok.Data;

import java.time.OffsetDateTime;



@Data
public abstract class BaseTimeDomain {
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    // 생성자, getter, setter 등 도메인 로직에 맞게 구현
}