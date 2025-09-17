package io.github.ruifoot.common.dto;

import io.github.ruifoot.common.response.ResponseCode;

public record ResponseDto<T>(
        int status,
        String message,
        T data
) {
    public static <T> ResponseDto<T> of(ResponseCode code, T data) {
        return new ResponseDto<>(code.getStatus(), code.getMessage(), data);
    }

    public static ResponseDto<Void> of(ResponseCode code) {
        return new ResponseDto<>(code.getStatus(), code.getMessage(), null);
    }

}
