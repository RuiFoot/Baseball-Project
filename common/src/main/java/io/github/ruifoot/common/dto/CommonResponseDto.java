package io.github.ruifoot.common.dto;

import io.github.ruifoot.common.response.ResponseCode;

public record CommonResponseDto<T>(
        int status,
        String message,
        T data
) {
    public static <T> CommonResponseDto<T> of(ResponseCode code, T data) {
        return new CommonResponseDto<>(code.getStatus(), code.getMessage(), data);
    }

    public static CommonResponseDto<Void> of(ResponseCode code) {
        return new CommonResponseDto<>(code.getStatus(), code.getMessage(), null);
    }

}
