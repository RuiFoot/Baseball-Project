package io.github.ruifoot.common.util;

import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;

public final class ApiResponseUtil {
    public static <T> ResponseEntity<CommonResponseDto<T>> success(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code, data));
    }

    public static ResponseEntity<CommonResponseDto<?>> success(ResponseCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code));
    }

    public static <T> ResponseEntity<CommonResponseDto<T>> fail(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code, data));
    }

    public static ResponseEntity<CommonResponseDto<Void>> fail(ResponseCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code));
    }
}
