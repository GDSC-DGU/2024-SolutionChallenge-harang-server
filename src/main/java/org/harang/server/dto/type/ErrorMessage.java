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

    // forbidden - 403
    FORBIDDEN("40301", HttpStatus.FORBIDDEN, "권한이 없습니다."),

    // not found - 404
    NOT_FOUND("40401", HttpStatus.NOT_FOUND, "리소스가 존재하지 않습니다."),

    // method not allowed - 409
    METHOD_NOT_ALLOWED("40901", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 메소드입니다."),

    // internal server error - 500
    INTERNAL_SERVER_ERROR("50001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    ;

    private String code;
    @JsonIgnore
    private HttpStatus status;
    private String message;

}
