package org.harang.server.dto.type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorMessage {

    // bad request - 400
    BAD_REQUEST("40001", HttpStatus.BAD_REQUEST, "올바르지 않은 요청입니다."),

    // unauthorized - 401
    UNAUTHORIZED("40101", HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),

    // JWT Error - 401
    INVALID_JWT("40102", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_JWT("40103", HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    UNSUPPORTED_JWT("40104", HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰입니다."),
    JWT_IS_EMPTY("40105", HttpStatus.UNAUTHORIZED, "토큰이 비어있습니다."),
    INVALID_TOKEN_TYPE("40106", HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰 타입입니다."),

    // Principal error - 401
    PRINCIPAL_IS_EMPTY("40107", HttpStatus.UNAUTHORIZED, "컨텍스트로부터 유저 정보를 가져올 수 없습니다."),

    // forbidden - 403
    FORBIDDEN("40301", HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // not found - 404
    NOT_FOUND("40401", HttpStatus.NOT_FOUND, "리소스가 존재하지 않습니다."),
    MEMBER_NOT_FOUND("40402", HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    POST_NOT_FOUND("40403", HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다."),
    MATCHING_NOT_FOUND("40404", HttpStatus.NOT_FOUND, "매칭이 존재하지 않습니다."),
    MEMBER_INFO_NOT_FOUND("40405", HttpStatus.NOT_FOUND, "사용자의 정보가 존재하지 않습니다."),


    // method not allowed - 409
    METHOD_NOT_ALLOWED("40901", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메소드입니다."),

    // internal server error - 500
    INTERNAL_SERVER_ERROR("50001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    POST_ALREADY_MATCHED("50002", HttpStatus.INTERNAL_SERVER_ERROR, "이미 매칭되었거나 매칭 종료된 게시글입니다."),
    POST_NOT_MATCHED("50003", HttpStatus.INTERNAL_SERVER_ERROR, "매칭 상태가 아닌 게시글은 매칭 종료 상태가 될 수 없습니다."),
    MATCHING_ALREADY_DONE("50004", HttpStatus.INTERNAL_SERVER_ERROR, "이미 종료된 매칭입니다."),
    ONLY_SPROUT_CAN_CREATE_MATCH("50005", HttpStatus.INTERNAL_SERVER_ERROR, "새싹인 유저만 매칭을 생성할 수 있습니다."),
    ONLY_WATERING_CAN_HELP_SPROUT("50006", HttpStatus.INTERNAL_SERVER_ERROR, "물뿌리개인 유저만 새싹을 도와줄 수 있습니다."),
    ;

    private String code;
    @JsonIgnore
    private HttpStatus status;
    private String message;

}
