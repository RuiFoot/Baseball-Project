package io.github.ruifoot.common.service.lmpl;

import io.github.ruifoot.common.dto.CommonResponseDto;
import io.github.ruifoot.common.response.ResponseCode;
import io.github.ruifoot.common.service.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseServiceImpl implements ResponseService {
    public <T> ResponseEntity<CommonResponseDto<T>> success(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code, data));
    }

    public ResponseEntity<CommonResponseDto<Void>> success(ResponseCode code) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code));
    }

    public <T> ResponseEntity<CommonResponseDto<T>> fail(ResponseCode code, T data) {
        return ResponseEntity
                .status(code.getStatus())
                .body(CommonResponseDto.of(code, data));
    }
}
