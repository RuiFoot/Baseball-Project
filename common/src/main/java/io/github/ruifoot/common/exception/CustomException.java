package io.github.ruifoot.common.exception;


import io.github.ruifoot.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ResponseCode code;
    private final String message;

    public CustomException(ResponseCode code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    public CustomException(ResponseCode code) {
        super(code.getMessage());
        this.code = code;
        this.message = code.getMessage();
    }

}
