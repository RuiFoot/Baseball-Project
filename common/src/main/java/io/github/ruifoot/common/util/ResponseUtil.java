package io.github.ruifoot.common.util;

import io.github.ruifoot.common.dto.ResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;


/**
 * Utility class for creating API responses.
 * 공통 응답 DTO -> ResponseEntity로 mapping을 위한 Util
 */
public final class ResponseUtil {
    public static <T> ResponseEntity<ResponseDto<?>> success(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(ResponseDto.of(code, data));
    }

    public static ResponseEntity<ResponseDto<?>> success(ResponseCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(ResponseDto.of(code));
    }

    public static <T> ResponseEntity<ResponseDto<?>> fail(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(ResponseDto.of(code, data));
    }

    public static ResponseEntity<ResponseDto<Void>> fail(ResponseCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(ResponseDto.of(code));
    }
}
