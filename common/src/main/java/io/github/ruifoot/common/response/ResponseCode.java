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

    INVALID_PARAMETER(400, "잘못된 파라미터입니다."),
    INVALID_REQUEST(400, "잘못된 요청입니다."),
    INVALID_REQUEST_BODY(400, "요청 본문이 올바르지 않습니다."),
    INVALID_REQUEST_HEADER(400, "요청 헤더가 올바르지 않습니다."),
    INVALID_REQUEST_PATH(400, "요청 경로가 올바르지 않습니다."),
    INVALID_REQUEST_QUERY(400, "요청 쿼리 파라미터가 올바르지 않습니다."),
    INVALID_PASSWORD(400, "비밀번호가 올바르지 않습니다."),

    VALIDATION_ERROR(400, "입력값이 유효하지 않습니다."),

    // JSON 파싱 에러 전용 코드 추가
    JSON_PARSE_ERROR(400, "요청 JSON 형식이 올바르지 않습니다."),

    UNAUTHORIZED(401, "인증 정보가 유효하지 않습니다."),

    USER_NOT_AUTHORIZED(403, "사용자가 인증되지 않았습니다."),
    FORBIDDEN(403, "접근 권한이 없습니다."),

    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    DUPLICATED_USER(409, "이미 존재하는 사용자입니다."),

    INTERNAL_ERROR(500, "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String message;
}
