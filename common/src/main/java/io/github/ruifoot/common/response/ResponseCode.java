package io.github.ruifoot.common.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    SUCCESS(200, "요청이 성공적으로 처리되었습니다."),
    LOGIN_SUCCESS(200, "로그인이 성공적으로 처리되었습니다."),
    LOGOUT_SUCCESS(200, "로그아웃이 성공적으로 처리되었습니다."),
    USER_UPDATE_SUCCESS(200, "사용자 정보가 성공적으로 업데이트되었습니다."),
    USER_DELETE_SUCCESS(200, "사용자 정보가 성공적으로 삭제되었습니다."),

    USER_CREATE_SUCCESS(201, "사용자 정보가 성공적으로 생성되었습니다."),



    //-----------------------------------------------------------------------

    // 400 BAD_REQUEST
    INVALID_PARAMETER(400, "Invalid parameter"),
    INVALID_REQUEST(400, "Invalid request"),
    INVALID_REQUEST_BODY(400, "Invalid request body"),
    INVALID_REQUEST_HEADER(400, "Invalid request header"),
    INVALID_REQUEST_PATH(400, "Invalid request path"),
    INVALID_REQUEST_QUERY(400, "Invalid request query"),
    INVALID_PASSWORD(400, "비밀번호가 올바르지 않습니다."),

    UNAUTHORIZED(401, "인증 정보가 유효하지 않습니다."),

    USER_NOT_AUTHORIZED(403, "사용자가 인증되지 않았습니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),

    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    DUPLICATED_USER(409, "이미 존재하는 사용자입니다."),

    INTERNAL_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;
}
