package io.github.ruifoot.common.exception;

import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponseDto<Void>> handleCustomException(CustomException e) {
        ResponseCode code = e.getCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponseDto<Void>> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(ResponseCode.INTERNAL_ERROR.getStatus())
                .body(CommonResponseDto.of(ResponseCode.INTERNAL_ERROR));
    }

}
