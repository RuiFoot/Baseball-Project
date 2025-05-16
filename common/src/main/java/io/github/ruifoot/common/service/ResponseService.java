package io.github.ruifoot.common.service;

import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import org.springframework.http.ResponseEntity;

public interface ResponseService {

    <T> ResponseEntity<CommonResponseDto<T>> success(ResponseCode code, T data);
    ResponseEntity<CommonResponseDto<Void>> success(ResponseCode code);
    <T> ResponseEntity<CommonResponseDto<T>> fail(ResponseCode code, T data);
}
