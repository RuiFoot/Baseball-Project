package io.github.ruifoot.common.exception;

import io.github.ruifoot.common.dto.ResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<Void>> handleCustomException(CustomException e) {
        ResponseCode code = e.getCode();
        return ResponseEntity
                .status(code.getStatus())
                .body(ResponseDto.of(code));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto<Void>> handleGeneralException(Exception e) {
        return ResponseEntity
                .status(ResponseCode.INTERNAL_ERROR.getStatus())
                .body(ResponseDto.of(ResponseCode.INTERNAL_ERROR));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ResponseCode code = ResponseCode.VALIDATION_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(new ResponseDto<>(code.getStatus(), errorMessage,null));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDto<Void>> handleJsonParseException(HttpMessageNotReadableException ex) {

        // 기본 한글 메시지 설정
        String errorMessage = "요청 본문(JSON)이 올바르지 않거나, 필수 항목이 누락되었습니다.";

        // 로그를 위한 상세 메시지
        String detailedMessage = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();

        // 한글 메시지 + 상세 메시지 합치기 (원하는 형식으로 편집 가능)
        String combinedMessage = errorMessage + " 상세: " + detailedMessage;

        ResponseCode code = ResponseCode.JSON_PARSE_ERROR; // 또는 따로 JsonParseError 등 정의 가능

        return ResponseEntity
                .status(code.getStatus())
                .body(new ResponseDto<>(code.getStatus(), combinedMessage, null));
    }

}
